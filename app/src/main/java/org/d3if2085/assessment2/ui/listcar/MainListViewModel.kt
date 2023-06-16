package org.d3if2085.assessment2.ui.listcar

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if2085.assessment2.model.Mobil
import org.d3if2085.assessment2.network.ApiStatus
import org.d3if2085.assessment2.network.MobilApi
import org.d3if2085.assessment2.network.UpdateWorker
import java.util.concurrent.TimeUnit

class MainListViewModel : ViewModel() {
    private val data = MutableLiveData<List<Mobil>>()
    private val status = MutableLiveData<ApiStatus>()

    init {
            retrieveData()
    }
    private fun retrieveData() {
        viewModelScope.launch (Dispatchers.IO) {
            status.postValue(ApiStatus.LOADING)
            try {
                data.postValue(MobilApi.service.getMobil())
                status.postValue(ApiStatus.SUCCESS)


            } catch (e: Exception) {
                Log.d("MainViewModel", "Failure: ${e.message}")
                status.postValue(ApiStatus.FAILED)

            }
        }
    }

    fun scheduleUpdater(app: Application) {
        val request = OneTimeWorkRequestBuilder<UpdateWorker>()
            .setInitialDelay(1, TimeUnit.MINUTES)
            .build()
        WorkManager.getInstance(app).enqueueUniqueWork(
            UpdateWorker.WORK_NAME,
            ExistingWorkPolicy.REPLACE,
            request
        )
    }
    // Data ini akan kita ambil dari server di langkah selanjutnya

    fun getData(): LiveData<List<Mobil>> = data
    fun getStatus(): LiveData<ApiStatus> = status

}