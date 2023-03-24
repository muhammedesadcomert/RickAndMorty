package com.invio.rickandmorty.di

import com.invio.rickandmorty.data.dto.character.CharacterResult
import com.invio.rickandmorty.data.dto.location.LocationResult
import com.invio.rickandmorty.domain.mapper.CharacterMapper
import com.invio.rickandmorty.domain.mapper.DomainMapper
import com.invio.rickandmorty.domain.mapper.LocationMapper
import com.invio.rickandmorty.domain.model.Character
import com.invio.rickandmorty.domain.model.Location
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
    abstract fun bindCharacterMapper(characterMapper: CharacterMapper): DomainMapper<CharacterResult, Character>

    @Binds
    @ViewModelScoped
    abstract fun bindLocationMapper(locationMapper: LocationMapper): DomainMapper<LocationResult, Location>
}