package com.muhammedesadcomert.rickandmorty.data.network

import com.muhammedesadcomert.rickandmorty.data.dto.CharacterResponse
import com.muhammedesadcomert.rickandmorty.data.dto.LocationResponse
import com.muhammedesadcomert.rickandmorty.data.dto.RickAndMortyResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(): Response<RickAndMortyResponse<CharacterResponse>>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: String): Response<CharacterResponse>

    @GET("character/{ids}")
    suspend fun getMultipleCharacters(@Path("ids") ids: String): Response<out Any>

    @GET("location")
    suspend fun getLocations(@Query("page") page: Int): Response<RickAndMortyResponse<LocationResponse>>
}