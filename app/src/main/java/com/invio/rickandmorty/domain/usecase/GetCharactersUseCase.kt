package com.invio.rickandmorty.domain.usecase

import com.invio.rickandmorty.data.repository.RickAndMortyRepository
import com.invio.rickandmorty.domain.mapper.CharacterMapper
import com.invio.rickandmorty.domain.model.Character
import com.invio.rickandmorty.util.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val rickAndMortyRepository: RickAndMortyRepository,
    private val characterMapper: CharacterMapper
) {
    operator fun invoke(): Flow<NetworkResponse<List<Character>>> = flow {
        emit(NetworkResponse.Loading)
        when (val result = rickAndMortyRepository.getCharacters()) {
            is NetworkResponse.Error -> emit(NetworkResponse.Error(result.errorMessage))
            is NetworkResponse.Success -> {
                result.data.results?.let {
                    emit(NetworkResponse.Success(characterMapper.toDomainList(result.data.results.filterNotNull())))
                }
            }
            else -> Unit
        }
    }
}