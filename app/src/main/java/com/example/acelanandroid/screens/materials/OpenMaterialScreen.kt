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
import com.example.acelanandroid.screens.tasks.DrawImage
import com.example.acelanandroid.screens.tasks.OpenTaskViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun OpenMaterialScreen(
    idMaterial: Int,
    modifier: Modifier = Modifier,
    openMaterialViewModel: OpenMaterialViewModel = hiltViewModel()
) {
    //val uiState by openMaterialViewModel.uiState
    val uiStateMain by openMaterialViewModel.uiStateMain
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        openMaterialViewModel.getToken()
    }
    openMaterialViewModel.getIdMaterial(idMaterial)
    coroutineScope.launch {
        openMaterialViewModel.getMaterialById()
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