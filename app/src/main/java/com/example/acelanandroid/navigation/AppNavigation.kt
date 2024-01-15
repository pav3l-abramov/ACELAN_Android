package com.example.acelanandroid.navigation

import android.annotation.SuppressLint
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.acelanandroid.screens.appScreens.HomeScreen
import com.example.acelanandroid.screens.appScreens.materials.MaterialsScreen
import com.example.acelanandroid.screens.appScreens.models.ModelsScreen
import com.example.acelanandroid.screens.appScreens.profile.ProfileScreen
import com.example.acelanandroid.screens.appScreens.tasks.TasksScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                screens.forEach { navItem ->
                    NavigationBarItem(selected =currentDestination?.hierarchy?.any{it.route==navItem.route}==true,
                        onClick = {
                                  navController.navigate(navItem.route){
                                      popUpTo(navController.graph.findStartDestination().id){
                                          saveState=true
                                      }
                                      launchSingleTop=true
                                      restoreState= true
                                  }
                        },
                        icon = { 
                            Icon(painter = painterResource(id = navItem.icon) ,
                                contentDescription = null)
                        },
                        label = { Text(text = navItem.title)}
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
                MaterialsScreen()
            }
            composable(route = BottomBarScreen.Tasks.route) {
                TasksScreen()
            }
            composable(route = BottomBarScreen.Models.route) {
                ModelsScreen()
            }
            composable(route = BottomBarScreen.Profile.route) {
                ProfileScreen()
            }

        }

    }
}