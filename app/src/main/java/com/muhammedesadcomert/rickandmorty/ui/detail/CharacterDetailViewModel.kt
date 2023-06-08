package com.muhammedesadcomert.rickandmorty.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class CharacterDetailViewModel @Inject constructor(private val repository: RickAndMortyRepository) :
    ViewModel() {

    private val _character = MutableStateFlow<UiState<Character>>(UiState.initial())
    val character: StateFlow<UiState<Character>> get() = _character

    fun getCharacter(id: String) {
        viewModelScope.launch {
            repository.getCharacter(id).collect {
                when (it) {
                    is NetworkResponse.Loading -> {
                        _character.value = UiState<Character>().copy(isLoading = true)
                    }

                    is NetworkResponse.Error -> {
                        _character.value = UiState<Character>().copy(errorMessage = it.errorMessage)
                    }

                    is NetworkResponse.Success -> {
                        _character.value = UiState<Character>().copy(data = it.data)
                    }
                }
            }
        }
    }
}