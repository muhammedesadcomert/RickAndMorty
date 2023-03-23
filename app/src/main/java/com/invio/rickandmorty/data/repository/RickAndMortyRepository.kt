package com.invio.rickandmorty.data.repository

import com.invio.rickandmorty.data.dto.CharactersResponse
import com.invio.rickandmorty.util.NetworkResponse

interface RickAndMortyRepository {
    suspend fun getCharacters(): NetworkResponse<CharactersResponse>
}