package com.muhammedesadcomert.rickandmorty.di

import com.muhammedesadcomert.rickandmorty.BuildConfig
import com.muhammedesadcomert.rickandmorty.data.network.InternetConnectionInterceptor
import com.muhammedesadcomert.rickandmorty.data.network.RickAndMortyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(internetConnectionInterceptor: InternetConnectionInterceptor) =
        OkHttpClient.Builder()
            .addInterceptor(internetConnectionInterceptor)
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.HEADERS)
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            ).build()

    @Provides
    @Singleton
    fun provideRickAndMortyApi(okHttpClient: OkHttpClient): RickAndMortyApi = Retrofit.Builder()
        .baseUrl(BuildConfig.RICK_AND_MORTY_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
        .create(RickAndMortyApi::class.java)
}