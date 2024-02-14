package com.example.acelanandroid.screens.tasks

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
import com.example.acelanandroid.common.composable.BasicButton
import com.example.acelanandroid.common.composable.TaskDetailCard
import com.example.acelanandroid.common.composable.TextCard
import com.example.acelanandroid.opengl.drawModel.img.DrawImage
import com.example.acelanandroid.common.ext.basicButton
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.OpenGLES20Activity
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun OpenTaskScreen(
    modifier: Modifier = Modifier,
    openTaskViewModel: OpenTaskViewModel = hiltViewModel(),
    idTask: Int,
    context: Context
) {
    val uiState by openTaskViewModel.uiState
    val uiStateMain by openTaskViewModel.uiStateMain
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        Log.d("tasks", "startDetailScreen1")
        openTaskViewModel.getToken()
        Log.d("tasks", "startDetailScreen2")
    }
    openTaskViewModel.getIdTask(idTask)
    coroutineScope.launch {
        Log.d("tasks", "startDetailScreen3")
        openTaskViewModel.getTaskById()
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TaskDetailCard("Name: ", uiStateMain.name.toString(), false, Modifier.fieldModifier())
        TaskDetailCard("Status: ", uiStateMain.status.toString(), false, Modifier.fieldModifier())
        TaskDetailCard("Start:  ", uiStateMain.started_at.toString(),true, Modifier.fieldModifier())
        TaskDetailCard("Finish: ", uiStateMain.finished_at.toString(),true,Modifier.fieldModifier())
        TaskDetailCard("File type: ", uiState.file_type.toString(), false, Modifier.fieldModifier())
        TaskDetailCard("File url: ", uiState.url.toString(), false, Modifier.fieldModifier())
        if (uiState.url != null) {
            when (uiState.file_type) {
                "jpg", "png" -> DrawImage(uiState.url!!, Modifier.fieldModifier())
                "ply","obj","stl" -> BasicButton("View model", Modifier.basicButton()) {
                    val intent= Intent(context, OpenGLES20Activity::class.java)
                    intent.putExtra("url", uiState.url)
                    intent.putExtra("type", uiState.file_type)
                    context.startActivity(intent)
                }
                else -> TextCard("I don't know how to draw this file",Modifier.fieldModifier())
            }
        } else {
            TextCard("There is nothing to draw in this task",Modifier.fieldModifier())
        }
    }
}

