package com.invio.rickandmorty.data.dto.character

import com.google.gson.annotations.SerializedName

data class CharacterResult(
    @SerializedName("created")
    val created: String?,
    @SerializedName("episode")
    val episode: List<String?>?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("image")
    val image: String?,
    @SerializedName("location")
    val location: LocationOrOrigin?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("origin")
    val origin: LocationOrOrigin?,
    @SerializedName("species")
    val species: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("url")
    val url: String?
)

data class LocationOrOrigin(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)