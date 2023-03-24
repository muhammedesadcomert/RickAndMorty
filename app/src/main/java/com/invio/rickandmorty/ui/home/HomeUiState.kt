package com.invio.rickandmorty.ui.home

data class HomeUiState<T>(
    val data: List<T> = arrayListOf(),
    val errorMessage: String = "",
    val isLoading: Boolean = false
) {
    fun initial() = HomeUiState<T>()
}
