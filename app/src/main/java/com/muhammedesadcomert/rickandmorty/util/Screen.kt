package com.muhammedesadcomert.rickandmorty.util

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Home : Screen("home")
    data object CharacterDetail : Screen("character_detail")
}