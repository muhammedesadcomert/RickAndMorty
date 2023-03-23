package com.invio.rickandmorty.di

import com.invio.rickandmorty.data.dto.Result
import com.invio.rickandmorty.domain.mapper.DomainMapper
import com.invio.rickandmorty.domain.mapper.RickAndMortyMapper
import com.invio.rickandmorty.domain.model.Character
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
    abstract fun bindRickAndMortyMapper(rickAndMortyMapper: RickAndMortyMapper): DomainMapper<Result, Character>
}