package com.example.acelanandroid.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.acelanandroid.screens.profile.LoginViewModel
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
) {
    val uiStateUser by loginViewModel.uiStateUser
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        Log.d("tasks", "startMain1")
        loginViewModel.checkUser()
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Image(
            painter = painterResource(id =if (isSystemInDarkTheme()) com.example.acelanandroid.R.drawable.logo_dark_theme else com.example.acelanandroid.R.drawable.logo_light_theme ),
            contentDescription = "",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
        )
    if (!uiStateUser.isActive ) {
            Text(text = "Go to profile and login")
        }
    else{
            Text(text = "Hello dear user!\n" +
                    "\n" +
                    "Welcome to the ACELAN mobile application! We are pleased to offer you a wide range of services and opportunities to make your life easier and more convenient.")
        }

    }
}
