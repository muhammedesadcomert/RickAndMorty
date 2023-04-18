package com.invio.rickandmorty.data.repository

import androidx.paging.PagingData
import com.invio.rickandmorty.domain.model.Character
import com.invio.rickandmorty.domain.model.Location
import com.invio.rickandmorty.util.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {
    fun getCharacters(): Flow<NetworkResponse<List<Character>>>
    fun getCharacter(id: String): Flow<NetworkResponse<Character>>
    fun getMultipleCharacters(urls: List<String>): Flow<NetworkResponse<List<Character>>>
    fun getLocations(): Flow<PagingData<Location>>
}