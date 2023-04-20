package com.invio.rickandmorty.domain.mapper

import com.invio.rickandmorty.data.dto.LocationResponse
import com.invio.rickandmorty.domain.model.Location
import javax.inject.Inject

class LocationMapper @Inject constructor() : DomainMapper<LocationResponse, Location> {
    override fun mapToDomainModel(data: LocationResponse?) = Location(
        id = data?.id ?: 0,
        name = data?.name.orEmpty(),
        residents = data?.residents?.filterNotNull().orEmpty()
    )

    override fun toDomainList(initial: List<LocationResponse?>?): List<Location> =
        initial?.mapNotNull(this::mapToDomainModel).orEmpty()
}