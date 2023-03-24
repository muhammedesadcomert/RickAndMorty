package com.invio.rickandmorty.data.network

import com.invio.rickandmorty.data.dto.character.CharacterResponse
import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyApi {
    @GET("character")
    suspend fun getCharacters(): Response<CharacterResponse>
}