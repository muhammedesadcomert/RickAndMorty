package com.invio.rickandmorty.di

import com.invio.rickandmorty.data.repository.RickAndMortyRepository
import com.invio.rickandmorty.data.repository.RickAndMortyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun bindRickAndMortyRepository(rickAndMortyRepositoryImpl: RickAndMortyRepositoryImpl): RickAndMortyRepository
}