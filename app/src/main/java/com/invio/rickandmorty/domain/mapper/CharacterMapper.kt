package com.invio.rickandmorty.domain.mapper

import com.invio.rickandmorty.data.dto.character.CharacterResult
import com.invio.rickandmorty.domain.model.Character
import com.invio.rickandmorty.util.parseIdsWithWhiteSpaces
import javax.inject.Inject

class CharacterMapper @Inject constructor() : DomainMapper<CharacterResult, Character> {
    override fun mapToDomainModel(data: CharacterResult?) = Character(
        id = data?.id.toString(),
        name = data?.name.orEmpty(),
        image = data?.image.orEmpty(),
        gender = data?.gender.orEmpty(),
        status = data?.status.orEmpty(),
        species = data?.species.orEmpty(),
        origin = data?.origin?.name.orEmpty(),
        location = data?.location?.name.orEmpty(),
        episodes = data?.episode?.filterNotNull()?.parseIdsWithWhiteSpaces().orEmpty(),
        created = data?.created.orEmpty()
    )

    override fun toDomainList(initial: List<CharacterResult?>?): List<Character> =
        initial?.mapNotNull(this::mapToDomainModel).orEmpty()
}