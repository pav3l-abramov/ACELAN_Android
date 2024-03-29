package com.example.acelanandroid.screens.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.acelanandroid.common.composable.BasicButton
import com.example.acelanandroid.common.composable.TextCardStandart
import com.example.acelanandroid.common.ext.basicButton
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.screens.MainViewModel
import kotlinx.coroutines.DelicateCoroutinesApi


@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel(),
    context: Context
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
        TextCardStandart(welcomeText,Modifier.fieldModifier())

        }
    else{
      BasicButton("Go to ACELAN", Modifier.basicButton()) {
          val url = "https://acelan.ru"
          val intent = Intent(Intent.ACTION_VIEW)
          intent.data = Uri.parse(url)
          context.startActivity(intent)
      }
        }



    }
}
