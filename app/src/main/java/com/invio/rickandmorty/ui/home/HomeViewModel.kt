package com.invio.rickandmorty.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.invio.rickandmorty.domain.model.Character
import com.invio.rickandmorty.domain.usecase.GetCharactersUseCase
import com.invio.rickandmorty.util.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getCharactersUseCase: GetCharactersUseCase) :
    ViewModel() {

    private val _characters: MutableStateFlow<NetworkResponse<List<Character>>> =
        MutableStateFlow(NetworkResponse.Loading)

    val characters: StateFlow<NetworkResponse<List<Character>>> get() = _characters

    init {
        getCharacters()
    }

    fun getCharacters() {
        viewModelScope.launch {
            getCharactersUseCase().collect { _characters.value = it }
        }
    }
}