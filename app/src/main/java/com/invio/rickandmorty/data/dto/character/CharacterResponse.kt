package com.invio.rickandmorty.data.dto.character

import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info")
    val info: Info?,
    @SerializedName("results")
    val results: List<Result?>?
)