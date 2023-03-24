package com.invio.rickandmorty.data.network

import com.invio.rickandmorty.data.dto.RickAndMortyResponse
import com.invio.rickandmorty.data.dto.character.CharacterResult
import com.invio.rickandmorty.data.dto.location.LocationResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(@Query("page") page: Int): Response<RickAndMortyResponse<CharacterResult>>

    @GET("location")
    suspend fun getLocations(@Query("page") page: Int): Response<RickAndMortyResponse<LocationResult>>
}