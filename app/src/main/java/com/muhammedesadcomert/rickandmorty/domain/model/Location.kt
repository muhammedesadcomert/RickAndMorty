package com.muhammedesadcomert.rickandmorty.domain.model

data class Location(
    val id: Int,
    val name: String,
    val residents: List<String>
)