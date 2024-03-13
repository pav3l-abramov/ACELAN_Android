package com.example.acelanandroid.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import com.example.acelanandroid.HOME_SCREEN
import com.example.acelanandroid.MATERIALS_SCREEN
import com.example.acelanandroid.MATERIAL_DETAILS
import com.example.acelanandroid.PROFILE_SCREEN
import com.example.acelanandroid.R
import com.example.acelanandroid.TASKS_SCREEN

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int
) {
    object Materials : BottomBarScreen(
        route = MATERIALS_SCREEN,
        title = "Material",
        icon = R.drawable.baseline_view_module_24
    )
    object Tasks : BottomBarScreen(
        route = TASKS_SCREEN,
        title = "Task",
        icon = R.drawable.baseline_task_24
    )
    object Profile : BottomBarScreen(
        route = PROFILE_SCREEN,
        title = "Profile",
        icon = R.drawable.baseline_person_24
    )
    object Home : BottomBarScreen(
        route = HOME_SCREEN,
        title = "Home",
        icon = R.drawable.baseline_home_24
    )
}
val screens = listOf(
    BottomBarScreen.Home,
    BottomBarScreen.Profile,
    BottomBarScreen.Materials,
    BottomBarScreen.Tasks,
)