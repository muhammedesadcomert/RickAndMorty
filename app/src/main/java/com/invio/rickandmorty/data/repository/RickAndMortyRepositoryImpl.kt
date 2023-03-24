package com.invio.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.invio.rickandmorty.data.network.RickAndMortyApi
import com.invio.rickandmorty.data.network.RickAndMortyPagingSource
import com.invio.rickandmorty.domain.mapper.CharacterMapper
import com.invio.rickandmorty.domain.mapper.LocationMapper
import com.invio.rickandmorty.domain.model.Character
import com.invio.rickandmorty.domain.model.Location
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RickAndMortyRepositoryImpl @Inject constructor(
    private val rickAndMortyApi: RickAndMortyApi,
    private val characterMapper: CharacterMapper,
    private val locationMapper: LocationMapper
) : RickAndMortyRepository {

    override fun getCharacters(): Flow<PagingData<Character>> =
        Pager(PagingConfig(DEFAULT_PAGE_SIZE)) {
            RickAndMortyPagingSource(characterMapper) { page ->
                rickAndMortyApi.getCharacters(page)
            }
        }.flow

    override fun getLocations(): Flow<PagingData<Location>> =
        Pager(PagingConfig(DEFAULT_PAGE_SIZE)) {
            RickAndMortyPagingSource(locationMapper) { page ->
                rickAndMortyApi.getLocations(page)
            }
        }.flow

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}