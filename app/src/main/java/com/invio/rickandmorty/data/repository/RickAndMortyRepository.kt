package com.invio.rickandmorty.data.repository

import androidx.paging.PagingData
import com.invio.rickandmorty.domain.model.Character
import com.invio.rickandmorty.domain.model.Location
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {
    fun getCharacters(): Flow<PagingData<Character>>
    fun getLocations(): Flow<PagingData<Location>>
}