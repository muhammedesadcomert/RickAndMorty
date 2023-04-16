package com.invio.rickandmorty.domain.mapper

import com.invio.rickandmorty.data.dto.location.LocationResult
import com.invio.rickandmorty.domain.model.Location
import javax.inject.Inject

class LocationMapper @Inject constructor() : DomainMapper<LocationResult, Location> {
    override fun mapToDomainModel(data: LocationResult?) = Location(
        id = data?.id ?: 0,
        name = data?.name.orEmpty(),
        residents = data?.residents?.filterNotNull().orEmpty()
    )

    override fun toDomainList(initial: List<LocationResult?>?): List<Location> =
        initial?.mapNotNull(this::mapToDomainModel).orEmpty()
}