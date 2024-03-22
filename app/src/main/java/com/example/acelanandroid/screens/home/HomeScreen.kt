package com.example.acelanandroid.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.acelanandroid.common.composable.TextCardStandart
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.screens.MainViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val userDB by mainViewModel.userDB.collectAsState()
    val checkUser by mainViewModel.checkUser.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Image(
            painter = painterResource(id =if (isSystemInDarkTheme()) com.example.acelanandroid.R.drawable.logo_dark_theme else com.example.acelanandroid.R.drawable.logo_light_theme ),
            contentDescription = "",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterHorizontally)
        )
  if (checkUser) {
        TextCardStandart("Go to profile and login",Modifier.fieldModifier())
        }
    else{
            Text(text = "Hello dear user!\n" +
                    "\n" +
                    "Welcome to the ACELAN mobile application! We are pleased to offer you a wide range of services and opportunities to make your life easier and more convenient.")
        }



    }
}
