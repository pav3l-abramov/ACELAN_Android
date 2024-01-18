package com.example.acelanandroid.screens.tasks

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun OpenTaskScreen(
    modifier: Modifier = Modifier,
    openTaskViewModel: OpenTaskViewModel = hiltViewModel(),
    idTask:Int
) {
    //tasksViewModel.getIdTask(idTask)
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        openTaskViewModel.getToken()
    }
    openTaskViewModel.getIdTask(idTask)
    coroutineScope.launch {
        Log.d("tasks", "startDetailScreen1")
        openTaskViewModel.getTaskById()
    }


    Text(text = idTask.toString())
}