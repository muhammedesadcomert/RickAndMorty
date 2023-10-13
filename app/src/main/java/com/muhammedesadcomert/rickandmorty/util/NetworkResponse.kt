package com.muhammedesadcomert.rickandmorty.util

sealed interface NetworkResponse<out T> {
    data object Loading : NetworkResponse<Nothing>
    data class Success<T>(val data: T) : NetworkResponse<T>
    data class Error(val errorMessage: String) : NetworkResponse<Nothing>
}