package com.acelan.acelanandroid.screens.home

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.acelan.acelanandroid.R
import com.acelan.acelanandroid.common.composable.BasicButton
import com.acelan.acelanandroid.common.composable.HomeCard
import com.acelan.acelanandroid.common.composable.TextCardStandart
import com.acelan.acelanandroid.common.ext.basicButton
import com.acelan.acelanandroid.common.ext.fieldModifier
import com.acelan.acelanandroid.screens.MainViewModel
import kotlinx.coroutines.DelicateCoroutinesApi


@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel= hiltViewModel(),
    context: Context
) {
    val userDB by mainViewModel.userDB.collectAsState()
    val checkUser by mainViewModel.checkUser.collectAsState()


    val listCard = listOf(
        HomeCardData("About the app", aboutApp,homeViewModel.booleanList[1],1),
        HomeCardData("What's on the material screen?", aboutMaterial,homeViewModel.booleanList[2],2),
        HomeCardData("What's on the task screen?", aboutTask,homeViewModel.booleanList[3],3),
        HomeCardData("How to build graphs?", howGraph,homeViewModel.booleanList[4],4),
        HomeCardData("How to view 3D models?", howView3DModel,homeViewModel.booleanList[5],5),
        )

    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){

        LazyColumn {
            item{
                Column(
                    modifier = modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally){
                Image(
                painter = painterResource(id =if (isSystemInDarkTheme()) R.drawable.logo_dark_theme else R.drawable.logo_light_theme ),
                contentDescription = "",
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.CenterHorizontally)
            )}}
            item{TextCardStandart(welcomeText,Modifier.fieldModifier())}
            if (checkUser) {
                item{
                    HomeCard(howLogin,"How Login?", homeViewModel.booleanList[0],{homeViewModel.onValueChange(0)},Modifier.fieldModifier())
                }
            }
            else{
                items(listCard){
                    HomeCard(it.content,it.title, it.isShow,{homeViewModel.onValueChange(it.indexParam)},Modifier.fieldModifier())
                }
            }

            item{
                BasicButton("Go to ACELAN", Modifier.basicButton()) {
                val url = "https://acelan.ru"
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                context.startActivity(intent)
            }}
        }



//  if (checkUser) {
//
//        }
//    else{
//      TextCardStandart(welcomeText,Modifier.fieldModifier())
//      HomeCard(howLogin,"How Login?", true,{},Modifier.fieldModifier())
//        }



    }
}
