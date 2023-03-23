package com.invio.rickandmorty.domain.usecase

import com.invio.rickandmorty.data.repository.RickAndMortyRepository
import com.invio.rickandmorty.domain.mapper.RickAndMortyMapper
import com.invio.rickandmorty.util.NetworkResponse
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository,
    private val domainMapper: RickAndMortyMapper
) {
    operator fun invoke() = flow {
        when (val result = rickAndMortyRepository.getCharacters()) {
            NetworkResponse.Loading -> NetworkResponse.Loading
            is NetworkResponse.Error -> emit(result)
            is NetworkResponse.Success -> {
                result.data.results?.let {
                    emit(NetworkResponse.Success(domainMapper.toDomainList(result.data.results.filterNotNull())))
                }
            }
        }
    }
}