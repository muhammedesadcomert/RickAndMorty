package com.muhammedesadcomert.rickandmorty.di

import com.muhammedesadcomert.rickandmorty.data.dto.CharacterResponse
import com.muhammedesadcomert.rickandmorty.data.dto.LocationResponse
import com.muhammedesadcomert.rickandmorty.domain.mapper.CharacterMapper
import com.muhammedesadcomert.rickandmorty.domain.mapper.DomainMapper
import com.muhammedesadcomert.rickandmorty.domain.mapper.LocationMapper
import com.muhammedesadcomert.rickandmorty.domain.model.Character
import com.muhammedesadcomert.rickandmorty.domain.model.Location
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class MapperModule {
    @Binds
    @ViewModelScoped
    abstract fun bindCharacterMapper(characterMapper: CharacterMapper): DomainMapper<CharacterResponse, Character>

    @Binds
    @ViewModelScoped
    abstract fun bindLocationMapper(locationMapper: LocationMapper): DomainMapper<LocationResponse, Location>
}