package com.invio.rickandmorty.navigation

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.invio.rickandmorty.ui.detail.CharacterDetail
import com.invio.rickandmorty.ui.home.Home
import com.invio.rickandmorty.ui.splash.SplashScreen
import com.invio.rickandmorty.util.Screen

@Composable
fun RickAndMortyNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            Screen.Home.route
        } else {
            Screen.Splash.route
        }
    ) {
        composable(Screen.Splash.route) {
            SplashScreen {
                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Splash.route) { inclusive = true }
                }
            }
        }
        composable(Screen.Home.route) {
            Home {
                navController.navigate(Screen.CharacterDetail.route + "/$it")
            }
        }
        composable(Screen.CharacterDetail.route + "/{characterId}") {
            CharacterDetail(characterId = it.arguments?.getString("characterId").orEmpty()) {
                navController.popBackStack()
            }
        }
    }
}