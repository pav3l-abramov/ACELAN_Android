package com.example.acelanandroid.navigation.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.acelanandroid.AUTHENTICATION
import com.example.acelanandroid.FORGOT_SCREEN
import com.example.acelanandroid.HOME_SCREEN
import com.example.acelanandroid.LOGIN_SCREEN
import com.example.acelanandroid.SIGN_UP_SCREEN
import com.example.acelanandroid.screens.auth.forgot.ForgotScreen
import com.example.acelanandroid.screens.auth.login.LoginScreen
import com.example.acelanandroid.screens.auth.signUp.SignUpScreen

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        route = AUTHENTICATION,
        startDestination = LOGIN_SCREEN
    ) {
        composable(route = LOGIN_SCREEN) {
            LoginScreen(
                onLoginClick = {
                    navController.popBackStack()
                    navController.navigate(HOME_SCREEN)
                },
                onSignUpClick = {
                    navController.navigate(SIGN_UP_SCREEN)
                },
                onForgotClick = {
                    navController.navigate(FORGOT_SCREEN)
                }
            )
        }
        composable(route = SIGN_UP_SCREEN) {
            SignUpScreen(onSignUpClick = {
                navController.popBackStack()
                navController.navigate(HOME_SCREEN)
            })
        }
        composable(route = FORGOT_SCREEN) {
            ForgotScreen(onForgotClick = {
                navController.popBackStack()
                navController.navigate(HOME_SCREEN)
            })
        }
    }
}