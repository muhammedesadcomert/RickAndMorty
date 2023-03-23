package com.invio.rickandmorty.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.invio.rickandmorty.ui.home.Home
import com.invio.rickandmorty.util.Screen

@Composable
fun RickAndMortyNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.HOME.name) {
        composable(Screen.HOME.name) {
            Home() {

            }
        }
    }
}