package com.muhammedesadcomert.rickandmorty.di

import com.muhammedesadcomert.rickandmorty.BuildConfig
import com.muhammedesadcomert.rickandmorty.data.network.InternetConnectionInterceptor
import com.muhammedesadcomert.rickandmorty.data.network.RickAndMortyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
object NetworkModule {
    @Provides
    @ViewModelScoped
    fun provideOkHttpClient(internetConnectionInterceptor: InternetConnectionInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(internetConnectionInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.HEADERS)
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            ).build()

    @Provides
    @ViewModelScoped
    fun provideRickAndMortyApi(okHttpClient: OkHttpClient): RickAndMortyApi = Retrofit.Builder()
        .baseUrl(BuildConfig.RICK_AND_MORTY_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(RickAndMortyApi::class.java)
}