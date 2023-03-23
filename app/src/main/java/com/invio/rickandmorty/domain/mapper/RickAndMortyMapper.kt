package com.invio.rickandmorty.domain.mapper

import com.invio.rickandmorty.data.dto.Result
import com.invio.rickandmorty.domain.model.Character
import javax.inject.Inject

class RickAndMortyMapper @Inject constructor() : DomainMapper<Result, Character> {
    override fun mapToDomainModel(data: Result) = Character(
        id = data.id ?: 0,
        name = data.name.orEmpty(),
        image = data.image.orEmpty(),
        gender = data.gender.orEmpty()
    )

    fun toDomainList(initial: List<Result>): List<Character> = initial.map(this::mapToDomainModel)
}