package com.muhammedesadcomert.rickandmorty.domain.mapper

import com.muhammedesadcomert.rickandmorty.data.dto.LocationResponse
import com.muhammedesadcomert.rickandmorty.domain.model.Location
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