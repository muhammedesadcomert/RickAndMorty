package com.invio.rickandmorty.domain.usecase

import com.invio.rickandmorty.data.repository.RickAndMortyRepository
import com.invio.rickandmorty.domain.mapper.LocationMapper
import com.invio.rickandmorty.domain.model.Location
import com.invio.rickandmorty.util.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository,
    private val locationMapper: LocationMapper
) {
    operator fun invoke(): Flow<NetworkResponse<List<Location>>> = flow {
        emit(NetworkResponse.Loading)
        when (val result = rickAndMortyRepository.getLocations()) {
            is NetworkResponse.Error -> emit(NetworkResponse.Error(result.errorMessage))
            is NetworkResponse.Success -> {
                result.data.results?.let {
                    emit(NetworkResponse.Success(locationMapper.toDomainList(result.data.results.filterNotNull())))
                }
            }
            else -> Unit
        }
    }
}