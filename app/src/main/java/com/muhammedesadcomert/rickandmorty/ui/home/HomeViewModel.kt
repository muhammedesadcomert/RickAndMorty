package com.muhammedesadcomert.rickandmorty.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.muhammedesadcomert.rickandmorty.data.repository.RickAndMortyRepository
import com.muhammedesadcomert.rickandmorty.domain.model.Character
import com.muhammedesadcomert.rickandmorty.ui.util.UiState
import com.muhammedesadcomert.rickandmorty.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RickAndMortyRepository) :
    ViewModel() {

    private val _characters: MutableStateFlow<UiState<List<Character>>> =
        MutableStateFlow(UiState.initial())
    val characters: StateFlow<UiState<List<Character>>> get() = _characters

    val locations = repository.getLocations().cachedIn(viewModelScope)

    init {
        getCharacters()
    }

    fun getCharacters() {
        viewModelScope.launch {
            repository.getCharacters().collect {
                when (it) {
                    NetworkResponse.Loading -> {
                        _characters.value = UiState<List<Character>>().copy(isLoading = true)
                    }

                    is NetworkResponse.Error -> {
                        _characters.value =
                            UiState<List<Character>>().copy(errorMessage = it.errorMessage)
                    }

                    is NetworkResponse.Success -> {
                        _characters.value = UiState<List<Character>>().copy(data = it.data)
                    }
                }
            }
        }
    }

    fun getMultipleCharacters(urls: List<String> = emptyList()) {
        viewModelScope.launch {
            repository.getMultipleCharacters(urls).collect {
                when (it) {
                    NetworkResponse.Loading -> {
                        _characters.value = UiState<List<Character>>().copy(isLoading = true)
                    }

                    is NetworkResponse.Error -> {
                        _characters.value =
                            UiState<List<Character>>().copy(errorMessage = it.errorMessage)
                    }

                    is NetworkResponse.Success -> {
                        _characters.value = UiState<List<Character>>().copy(data = it.data)
                    }
                }
            }
        }
    }
}