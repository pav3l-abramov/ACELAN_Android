package com.example.acelanandroid.navigation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.acelanandroid.MODEL_SCREEN
import com.example.acelanandroid.OPEN_MATERIAL_SCREEN
import com.example.acelanandroid.OPEN_TASK_SCREEN
import com.example.acelanandroid.screens.home.HomeScreen
import com.example.acelanandroid.screens.materials.MaterialsScreen
import com.example.acelanandroid.screens.materials.OpenMaterialScreen
import com.example.acelanandroid.screens.profile.ProfileScreen
import com.example.acelanandroid.screens.tasks.OpenTaskScreen
import com.example.acelanandroid.screens.tasks.TasksScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation(context:Context) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                screens.forEach { navItem ->
                    NavigationBarItem(selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                        onClick = {
                            navController.navigate(navItem.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(id = navItem.icon),
                                contentDescription = null
                            )
                        },
                        label = { Text(text = navItem.title) }
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = BottomBarScreen.Home.route,
            modifier = Modifier.padding(paddingValues)
        ) {

            composable(route = BottomBarScreen.Home.route) {
                HomeScreen()
            }


            composable(route = BottomBarScreen.Materials.route) {
                MaterialsScreen(navController = navController)
            }
            composable(route = "$OPEN_MATERIAL_SCREEN/{id}", arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                }
            )) { idMaterial ->
                idMaterial.arguments?.getInt("id")?.let { OpenMaterialScreen(idMaterial = it) }
            }


            composable(route = BottomBarScreen.Tasks.route) {
                TasksScreen(navController = navController)
            }
            composable(route = "$OPEN_TASK_SCREEN/{id}", arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                }
            )) { idTask ->
                idTask.arguments?.getInt("id")
                    ?.let { OpenTaskScreen(idTask = it, context = context) }
            }


            composable(route = BottomBarScreen.Profile.route) {
                ProfileScreen(context = context)
            }
        }

    }
}