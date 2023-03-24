package com.invio.rickandmorty.domain.mapper

import com.invio.rickandmorty.data.dto.character.CharacterResult
import com.invio.rickandmorty.domain.model.Character
import javax.inject.Inject

class CharacterMapper @Inject constructor() : DomainMapper<CharacterResult, Character> {
    override fun mapToDomainModel(data: CharacterResult) = Character(
        id = data.id ?: 0,
        name = data.name.orEmpty(),
        image = data.image.orEmpty(),
        gender = data.gender.orEmpty()
    )

    override fun toDomainList(initial: List<CharacterResult>): List<Character> =
        initial.map(this::mapToDomainModel)
}