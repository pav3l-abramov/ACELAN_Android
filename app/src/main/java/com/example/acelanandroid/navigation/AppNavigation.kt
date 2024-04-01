package com.example.acelanandroid.navigation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.acelanandroid.GRAPH_SCREEN
import com.example.acelanandroid.GRAPH_SETTING_SCREEN
import com.example.acelanandroid.OPEN_MATERIAL_SCREEN
import com.example.acelanandroid.OPEN_TASK_SCREEN
import com.example.acelanandroid.SPLASH_SCREEN
import com.example.acelanandroid.screens.MainViewModel
import com.example.acelanandroid.screens.home.HomeScreen
import com.example.acelanandroid.screens.materials.GraphScreen
import com.example.acelanandroid.screens.materials.GraphSettingScreen
import com.example.acelanandroid.screens.materials.MaterialsScreen
import com.example.acelanandroid.screens.materials.OpenMaterialScreen
import com.example.acelanandroid.screens.profile.ProfileScreen
import com.example.acelanandroid.screens.splash.SplashScreen
import com.example.acelanandroid.screens.tasks.OpenTaskScreen
import com.example.acelanandroid.screens.tasks.TasksScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavigation(context:Context) {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val mainViewModel : MainViewModel= hiltViewModel()
    val checkOpenApp by mainViewModel.checkOpenApp.collectAsState()
    Scaffold(
        bottomBar = {
            if (currentDestination != null) {
                if(currentDestination.route?.contains(OPEN_MATERIAL_SCREEN, ignoreCase = true) == false &&
                    currentDestination.route?.contains(GRAPH_SCREEN, ignoreCase = true) == false &&
                    currentDestination.route?.contains(GRAPH_SETTING_SCREEN, ignoreCase = true) == false &&
                    currentDestination.route?.contains(SPLASH_SCREEN, ignoreCase = true) == false &&
                    currentDestination.route?.contains(OPEN_TASK_SCREEN, ignoreCase = true) == false){
            NavigationBar {
                screens.forEach { navItem ->
                    NavigationBarItem(selected = currentDestination.hierarchy.any { it.route == navItem.route },
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
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = if (checkOpenApp) {
                BottomBarScreen.Home.route
            } else {
                SPLASH_SCREEN
            },
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(route = SPLASH_SCREEN) {
                SplashScreen(mainViewModel = mainViewModel,navController = navController)
            }
            composable(route = BottomBarScreen.Home.route) {
                 HomeScreen(mainViewModel = mainViewModel, context = context)

            }
            composable(route = BottomBarScreen.Materials.route) {
                EnterAnimation{MaterialsScreen(navController = navController, context = context,mainViewModel = mainViewModel)}

            }
            composable(route = "$OPEN_MATERIAL_SCREEN/{id}", arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                }
            )) { idMaterial ->
                idMaterial.arguments?.getInt("id")?.let {  EnterAnimation{OpenMaterialScreen(idMaterial = it, context = context,navController = navController,mainViewModel = mainViewModel) }}
            }


            composable(route = BottomBarScreen.Tasks.route) {
                EnterAnimation{TasksScreen(navController = navController, context = context,mainViewModel = mainViewModel)}
            }
            composable(route = "$OPEN_TASK_SCREEN/{id}", arguments = listOf(
                navArgument(name = "id") {
                    type = NavType.IntType
                }
            )) { idTask ->
                idTask.arguments?.getInt("id")
                    ?.let {  EnterAnimation{OpenTaskScreen(idTask = it, context = context,navController = navController,mainViewModel = mainViewModel) }}
            }


            composable(route = BottomBarScreen.Profile.route) {
                ProfileScreen(context = context,mainViewModel = mainViewModel)
            }
            composable(route = GRAPH_SCREEN) {
                GraphScreen(context = context,navController = navController,mainViewModel = mainViewModel)
            }
            composable(route = GRAPH_SETTING_SCREEN) {
                GraphSettingScreen(mainViewModel = mainViewModel,navController = navController,context = context)
            }
        }

    }

}
@Composable
fun EnterAnimation(content: @Composable () -> Unit) {
    AnimatedVisibility(
        visibleState = MutableTransitionState(
            initialState = false
        ).apply { targetState = true },
        modifier = Modifier,
        enter = slideInVertically(
            initialOffsetY = { -1 }
        ) + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(initialAlpha = 0.0f),
        exit = slideOutVertically()  + fadeOut(),
    ) {
        content()
    }
}