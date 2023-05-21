package org.d3if2085.assessment2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if2085.assessment2.database.entity.User
import org.d3if2085.assessment2.repository.UserRepository

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    var getAllUser: LiveData<List<User>> = repository.getAllUser

    fun getOneUser(id: Long): LiveData<User> {
        return repository.getOneUser(id)
    }

    fun getUserByUsername(username: String): LiveData<User> {
        return repository.getUserByUsername(username)
    }

    fun insertUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertUser(user)
    }

    fun updateUser(user: User) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateUser(user)
    }
}