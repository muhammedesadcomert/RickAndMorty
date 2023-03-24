package com.invio.rickandmorty.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.invio.rickandmorty.data.repository.RickAndMortyRepository
import com.invio.rickandmorty.domain.model.Character
import com.invio.rickandmorty.domain.model.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: RickAndMortyRepository) :
    ViewModel() {

    fun getCharacters(): Flow<PagingData<Character>> =
        repository.getCharacters().cachedIn(viewModelScope)

    fun getLocations(): Flow<PagingData<Location>> =
        repository.getLocations().cachedIn(viewModelScope)
}