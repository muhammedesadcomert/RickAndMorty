package com.muhammedesadcomert.rickandmorty.util

import retrofit2.Response

object SafeApiCall {
    inline fun <T> safeApiCall(block: () -> Response<T>): NetworkResponse<T> {
        return try {
            val response = block()
            if (response.isSuccessful) {
                NetworkResponse.Success(response.body()!!)
            } else {
                NetworkResponse.Error(response.message())
            }
        } catch (e: Exception) {
            NetworkResponse.Error(e.message.orEmpty())
        }
    }
}