package com.example.acelanandroid.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.acelanandroid.AUTHENTICATION
import com.example.acelanandroid.HOME_SCREEN
import com.example.acelanandroid.MATERIALS_SCREEN
import com.example.acelanandroid.MODELS_SCREEN
import com.example.acelanandroid.ROOT
import com.example.acelanandroid.TASKS_SCREEN
import com.example.acelanandroid.navigation.auth.authNavGraph
import com.example.acelanandroid.screens.home.HomeScreen

@Composable
fun NavigationGraph(navController : NavHostController) {

    NavHost(
        navController = navController,
        route = ROOT,
        startDestination = AUTHENTICATION
    ) {
        authNavGraph(navController = navController)
        composable(route = HOME_SCREEN) {
            HomeScreen(
                onClickMaterials = {
                    navController.navigate(MATERIALS_SCREEN)
                },
                onClickTasks = {
                    navController.navigate(TASKS_SCREEN)
                },
                onClickModels = {
                    navController.navigate(MODELS_SCREEN)
                }

            )
        }
    }
}