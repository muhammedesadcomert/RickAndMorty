package com.invio.rickandmorty.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.invio.rickandmorty.ui.detail.CharacterDetail
import com.invio.rickandmorty.ui.home.Home
import com.invio.rickandmorty.util.Screen

@Composable
fun RickAndMortyNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HOME.name) {
        composable(Screen.HOME.name) {
            Home {
                navController.navigate(Screen.CHARACTER_DETAIL.name + "/$it")
            }
        }
        composable(Screen.CHARACTER_DETAIL.name + "/{characterId}") {
            CharacterDetail(characterId = it.arguments?.getString("characterId").orEmpty()) {
                navController.popBackStack()
            }
        }
    }
}