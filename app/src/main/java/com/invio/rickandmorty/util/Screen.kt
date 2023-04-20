package com.invio.rickandmorty.util

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object CharacterDetail : Screen("character_detail")
}