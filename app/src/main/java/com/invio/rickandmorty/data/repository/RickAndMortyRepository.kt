package com.invio.rickandmorty.data.repository

import com.invio.rickandmorty.data.dto.RickAndMortyResponse
import com.invio.rickandmorty.data.dto.character.CharacterResult
import com.invio.rickandmorty.data.dto.location.LocationResult
import com.invio.rickandmorty.util.NetworkResponse

interface RickAndMortyRepository {
    suspend fun getCharacters(): NetworkResponse<RickAndMortyResponse<CharacterResult>>
    suspend fun getLocations(): NetworkResponse<RickAndMortyResponse<LocationResult>>
}