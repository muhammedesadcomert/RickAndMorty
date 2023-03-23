package com.invio.rickandmorty.util

sealed interface NetworkResponse<out T> {
    object Loading : NetworkResponse<Nothing>
    data class Success<T>(val data: T) : NetworkResponse<T>
    data class Error(val errorMessage: String) : NetworkResponse<Nothing>
}