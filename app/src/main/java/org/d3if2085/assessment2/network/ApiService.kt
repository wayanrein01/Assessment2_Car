package org.d3if2085.assessment2.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if2085.assessment2.model.Mobil
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://raw.githubusercontent.com/wayanrein01/Assessment03/main/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface ApiService {
    @GET("car.json")
    suspend fun getMobil(): List<Mobil>
}

object MobilApi {
    val service: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    fun getMobilUrl(nama: String): String {
        return "$BASE_URL$nama"
    }

}

enum class ApiStatus { LOADING, SUCCESS, FAILED }

