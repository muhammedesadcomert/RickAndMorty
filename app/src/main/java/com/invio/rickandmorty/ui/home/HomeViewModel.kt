package com.invio.rickandmorty.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.invio.rickandmorty.domain.model.Character
import com.invio.rickandmorty.domain.model.Location
import com.invio.rickandmorty.domain.usecase.GetCharactersUseCase
import com.invio.rickandmorty.domain.usecase.GetLocationsUseCase
import com.invio.rickandmorty.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getLocationsUseCase: GetLocationsUseCase
) : ViewModel() {

    private val _characters: MutableStateFlow<HomeUiState<Character>> =
        MutableStateFlow(HomeUiState<Character>().initial())
    val characters: StateFlow<HomeUiState<Character>> get() = _characters

    private val _locations: MutableStateFlow<HomeUiState<Location>> =
        MutableStateFlow(HomeUiState<Location>().initial())
    val locations: StateFlow<HomeUiState<Location>> get() = _locations

    init {
        getCharacters()
        getLocations()
    }

    fun getCharacters() {
        viewModelScope.launch {
            getCharactersUseCase().collect {
                when (it) {
                    NetworkResponse.Loading -> {
                        _characters.value = HomeUiState<Character>().copy(isLoading = true)
                    }
                    is NetworkResponse.Error -> {
                        _characters.value =
                            HomeUiState<Character>().copy(errorMessage = it.errorMessage)
                    }
                    is NetworkResponse.Success -> {
                        _characters.value = HomeUiState<Character>().copy(data = it.data)
                    }
                }
            }
        }
    }

    fun getLocations() {
        viewModelScope.launch {
            getLocationsUseCase().collect {
                when (it) {
                    NetworkResponse.Loading -> {
                        _locations.value = HomeUiState<Location>().copy(isLoading = true)
                    }
                    is NetworkResponse.Error -> {
                        _locations.value =
                            HomeUiState<Location>().copy(errorMessage = it.errorMessage)
                    }
                    is NetworkResponse.Success -> {
                        _locations.value = HomeUiState<Location>().copy(data = it.data)
                    }
                }
            }
        }
    }
}