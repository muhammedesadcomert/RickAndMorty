package com.invio.rickandmorty.data.repository

import com.invio.rickandmorty.data.dto.RickAndMortyResponse
import com.invio.rickandmorty.data.dto.character.CharacterResult
import com.invio.rickandmorty.data.dto.location.LocationResult
import com.invio.rickandmorty.data.network.RickAndMortyApi
import com.invio.rickandmorty.util.NetworkResponse
import com.invio.rickandmorty.util.SafeApiCall.safeApiCall
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RickAndMortyRepositoryImpl @Inject constructor(private val rickAndMortyApi: RickAndMortyApi) :
    RickAndMortyRepository {
    override suspend fun getCharacters(): NetworkResponse<RickAndMortyResponse<CharacterResult>> =
        withContext(Dispatchers.IO) {
            safeApiCall { rickAndMortyApi.getCharacters() }
        }

    override suspend fun getLocations(): NetworkResponse<RickAndMortyResponse<LocationResult>> =
        withContext(Dispatchers.IO) {
            safeApiCall { rickAndMortyApi.getLocations() }
        }
}