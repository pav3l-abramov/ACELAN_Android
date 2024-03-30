package com.example.acelanandroid.screens.splash

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.acelanandroid.HOME_SCREEN
import com.example.acelanandroid.R
import com.example.acelanandroid.screens.MainViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen (
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel()){
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    // Animation
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            // tween Animation
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                }))
        navController.navigate(HOME_SCREEN)
        mainViewModel.isOpenApp()
    }

    // Image
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()) {
        // Change the logo
        Image(
            painter = painterResource(id =if (isSystemInDarkTheme()) R.drawable.logo_dark_theme else R.drawable.logo_light_theme ),
            contentDescription = "Logo",
            modifier = Modifier.scale(scale.value))
    }

}