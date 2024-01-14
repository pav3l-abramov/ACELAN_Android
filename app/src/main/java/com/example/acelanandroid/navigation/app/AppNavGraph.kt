package com.example.acelanandroid.navigation.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.acelanandroid.HOME_SCREEN
import com.example.acelanandroid.MATERIALS_SCREEN
import com.example.acelanandroid.MODELS_SCREEN
import com.example.acelanandroid.POFILE_SCREEN
import com.example.acelanandroid.TASKS_SCREEN
import com.example.acelanandroid.screens.home.HomeScreen
import com.example.acelanandroid.screens.home.tasks.TasksScreen
import com.example.acelanandroid.screens.profile.ProfileScreen

@Composable
fun AppNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = HOME_SCREEN,
        startDestination = HOME_SCREEN
    ) {
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
                },
            )
        }
        composable(route = POFILE_SCREEN) {
            ProfileScreen()
        }
        //detailsNavGraph(navController = navController)
    }
}