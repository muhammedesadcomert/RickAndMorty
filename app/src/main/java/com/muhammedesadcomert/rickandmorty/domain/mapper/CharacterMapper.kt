package com.muhammedesadcomert.rickandmorty.domain.mapper

import com.muhammedesadcomert.rickandmorty.data.dto.CharacterResponse
import com.muhammedesadcomert.rickandmorty.domain.model.Character
import com.muhammedesadcomert.rickandmorty.util.parseIdsWithWhiteSpaces
import javax.inject.Inject

class CharacterMapper @Inject constructor() : DomainMapper<CharacterResponse, Character> {
    override fun mapToDomainModel(data: CharacterResponse?) = Character(
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

    override fun toDomainList(initial: List<CharacterResponse?>?): List<Character> =
        initial?.mapNotNull(this::mapToDomainModel).orEmpty()
}