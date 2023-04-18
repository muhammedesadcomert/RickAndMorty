package com.invio.rickandmorty.ui

data class UiState<T>(
    val data: T? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false
) {
    companion object {
        fun <T> initial() = UiState<T>()
    }
}
