package com.muhammedesadcomert.rickandmorty.util

import com.muhammedesadcomert.rickandmorty.data.network.NoInternetException
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

object SafeApiCall {
    inline fun <T> safeApiCall(block: () -> Response<T>): NetworkResponse<T> {
        return try {
            val response = block()
            if (response.isSuccessful) {
                NetworkResponse.Success(response.body()!!)
            } else {
                NetworkResponse.Error(response.message())
            }
        } catch (e: IOException) {
            NetworkResponse.Error(e.message.orEmpty())
        } catch (e: HttpException) {
            NetworkResponse.Error(e.message.orEmpty())
        } catch (e: Exception) {
            NetworkResponse.Error(e.message.orEmpty())
        } catch (e: NoInternetException) {
            NetworkResponse.Error(e.message)
        }
    }
}