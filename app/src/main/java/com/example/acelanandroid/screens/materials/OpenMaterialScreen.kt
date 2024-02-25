package com.example.acelanandroid.screens.materials

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.acelanandroid.OpenGLES20Activity
import com.example.acelanandroid.common.composable.BasicButton
import com.example.acelanandroid.common.composable.MaterialDetailCard
import com.example.acelanandroid.common.composable.TaskDetailCard
import com.example.acelanandroid.common.composable.TextCard
import com.example.acelanandroid.common.composable.TextCardStandart
import com.example.acelanandroid.common.ext.basicButton
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.data.UserData
import com.example.acelanandroid.screens.MainViewModel
import com.example.acelanandroid.screens.tasks.DrawImage
import com.example.acelanandroid.screens.tasks.OpenTaskViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun OpenMaterialScreen(
    idMaterial: Int,
    modifier: Modifier = Modifier,
    openMaterialViewModel: OpenMaterialViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    //val uiState by openMaterialViewModel.uiState
    val uiStateMain by openMaterialViewModel.uiStateMain
    val userDB = mainViewModel.getUserDB.collectAsState(initial = UserData())
    val checkUser by mainViewModel.checkUser
    LaunchedEffect(Unit) {
        GlobalScope.async {
            mainViewModel.userIsExist()
        }
    }
    openMaterialViewModel.getIdMaterial(idMaterial)
    GlobalScope.launch {
        userDB.value.token?.let { openMaterialViewModel.getMaterialById(it,idMaterial) }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MaterialDetailCard("Name: ", uiStateMain.name.toString(), false, Modifier.fieldModifier())
        MaterialDetailCard("Type: ", uiStateMain.type.toString(), false, Modifier.fieldModifier())
        MaterialDetailCard("Create: ", uiStateMain.created_at.toString(), true, Modifier.fieldModifier())
        MaterialDetailCard("Update: ", uiStateMain.updated_at.toString(), true, Modifier.fieldModifier())
        MaterialDetailCard("Core: ", uiStateMain.core.toString(), false, Modifier.fieldModifier())
        MaterialDetailCard("Source: ", uiStateMain.source.toString(), false, Modifier.fieldModifier())
        TextCardStandart("Properties",Modifier.fieldModifier())
        MaterialDetailCard("Young Modulus: ", uiStateMain.young.toString(), false, Modifier.fieldModifier())
        MaterialDetailCard("Poison Coefficient: ", uiStateMain.poison.toString(), false, Modifier.fieldModifier())
    }

}