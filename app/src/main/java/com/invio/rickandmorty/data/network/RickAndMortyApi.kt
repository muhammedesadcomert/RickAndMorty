package com.invio.rickandmorty.data.network

import com.invio.rickandmorty.data.dto.RickAndMortyResponse
import com.invio.rickandmorty.data.dto.character.CharacterResult
import com.invio.rickandmorty.data.dto.location.LocationResult
import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(): Response<RickAndMortyResponse<CharacterResult>>

    @GET("location")
    suspend fun getLocations(): Response<RickAndMortyResponse<LocationResult>>
}