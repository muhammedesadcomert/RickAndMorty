package com.invio.rickandmorty.domain.mapper

interface DomainMapper<Dto, DomainModel> {
    fun mapToDomainModel(data: Dto): DomainModel
}