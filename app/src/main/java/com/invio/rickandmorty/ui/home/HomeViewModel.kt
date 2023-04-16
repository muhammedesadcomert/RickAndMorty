package com.invio.rickandmorty.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.invio.rickandmorty.data.repository.RickAndMortyRepository
import com.invio.rickandmorty.domain.model.Character
import com.invio.rickandmorty.domain.model.Location
import com.invio.rickandmorty.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RickAndMortyRepository) :
    ViewModel() {

    private val _characters: MutableStateFlow<HomeUiState<Character>> =
        MutableStateFlow(HomeUiState<Character>().initial())
    val characters: StateFlow<HomeUiState<Character>> get() = _characters

    init {
        getCharacters()
    }

    fun getCharacters() {
        viewModelScope.launch {
            repository.getCharacters().collect {
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

    fun getMultipleCharacters(urls: List<String> = emptyList()) {
        viewModelScope.launch {
            repository.getMultipleCharacters(urls).collect {
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

    fun getLocations(): Flow<PagingData<Location>> =
        repository.getLocations().cachedIn(viewModelScope)
}