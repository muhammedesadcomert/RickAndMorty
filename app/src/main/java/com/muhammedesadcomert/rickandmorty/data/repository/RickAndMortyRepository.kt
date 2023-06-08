package com.muhammedesadcomert.rickandmorty.data.repository

import androidx.paging.PagingData
import com.muhammedesadcomert.rickandmorty.domain.model.Character
import com.muhammedesadcomert.rickandmorty.domain.model.Location
import com.muhammedesadcomert.rickandmorty.util.NetworkResponse
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepository {
    fun getCharacters(): Flow<NetworkResponse<List<Character>>>
    fun getCharacter(id: String): Flow<NetworkResponse<Character>>
    fun getMultipleCharacters(urls: List<String>): Flow<NetworkResponse<List<Character>>>
    fun getLocations(): Flow<PagingData<Location>>
}