package com.muhammedesadcomert.rickandmorty.data.dto

import com.google.gson.annotations.SerializedName

data class RickAndMortyResponse<out T>(
    @SerializedName("info")
    val info: Info?,
    @SerializedName("results")
    val results: List<T?>?
)

data class Info(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("next")
    val next: String?,
    @SerializedName("pages")
    val pages: Int?,
    @SerializedName("prev")
    val prev: Any?
)