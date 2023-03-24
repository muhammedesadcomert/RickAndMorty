package com.invio.rickandmorty.data.dto

import com.google.gson.annotations.SerializedName

data class RickAndMortyResponse<out T>(
    @SerializedName("info")
    val info: Info?,
    @SerializedName("results")
    val results: List<T?>?
)