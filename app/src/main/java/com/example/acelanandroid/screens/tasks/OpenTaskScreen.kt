package com.example.acelanandroid.screens.tasks

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import com.example.acelanandroid.common.composable.BasicButton
import com.example.acelanandroid.common.composable.TaskDetailCard
import com.example.acelanandroid.common.composable.TextCard
import com.example.acelanandroid.common.ext.basicButton
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.OpenGLES20Activity
import com.example.acelanandroid.common.composable.TextCardStandart
import com.example.acelanandroid.data.TaskMain
import com.example.acelanandroid.data.UserData
import com.example.acelanandroid.retrofit.GetStateTaskDetail
import com.example.acelanandroid.retrofit.GetStateTasks
import com.example.acelanandroid.screens.MainViewModel
import com.example.acelanandroid.screens.profile.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun OpenTaskScreen(
    modifier: Modifier = Modifier,
    openTaskViewModel: OpenTaskViewModel = hiltViewModel(),
    idTask: Int,
    context: Context,
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val taskDB = mainViewModel.getTaskByID(idTask).collectAsState(initial = TaskMain())
    val userDB = mainViewModel.getUserDB.collectAsState(initial = UserData())
    val checkUser by mainViewModel.checkUser
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(Unit) {
        mainViewModel.userIsExist()

    }

//    val uiState by openTaskViewModel.uiState
//    val uiStateMain by openTaskViewModel.uiStateMain
    if (!checkUser) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextCardStandart("Go to profile and login", Modifier.fieldModifier())
        }

    } else {
        LaunchedEffect(Unit) {
            openTaskViewModel.getListTaskDetailWithRetry(userDB.value.token.toString(), idTask)
        }
        openTaskViewModel.taskDetailState.observe(lifecycleOwner) { state ->
            Log.d("start", state.toString())
            when (state) {
                GetStateTaskDetail.Loading -> {
                    Log.d("Loading", state.toString())
                }

                is GetStateTaskDetail.Success -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        Log.d("Success2", state.toString())
                        val taskDetail = state.taskDetails

                        mainViewModel.updateTaskDetail(
                            taskDetail.artifacts?.takeIf { it.isNotEmpty() }?.get(0)?.file_type,
                            taskDetail.artifacts?.takeIf { it.isNotEmpty() }?.get(0)?.url,
                            taskDetail.figures?.takeIf { it.isNotEmpty() }?.get(0)?.data?.x,
                            taskDetail.figures?.takeIf { it.isNotEmpty() }?.get(0)?.data?.y,
                            taskDetail.id!!
                        )
                    }

                }

                is GetStateTaskDetail.Error -> {
                    Log.d("Error", state.toString())
                    val error = state.error
                }
            }
        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Text(text = dataList.toString())
            TaskDetailCard("Name: ", taskDB.value.name.toString(), false, Modifier.fieldModifier())
            TaskDetailCard(
                "Status: ",
                taskDB.value.status.toString(),
                false,
                Modifier.fieldModifier()
            )
            TaskDetailCard(
                "Start:  ",
                taskDB.value.started_at.toString(),
                true,
                Modifier.fieldModifier()
            )
            TaskDetailCard(
                "Finish: ",
                taskDB.value.finished_at.toString(),
                true,
                Modifier.fieldModifier()
            )
            TaskDetailCard(
                "File type: ",
                taskDB.value.file_type.toString(),
                false,
                Modifier.fieldModifier()
            )
            TaskDetailCard(
                "File url: ",
                taskDB.value.url.toString(),
                false,
                Modifier.fieldModifier()
            )
            if (taskDB.value.url != null) {
                when (taskDB.value.file_type) {
                    "jpg", "png" -> DrawImage(taskDB.value.url!!, Modifier.fieldModifier())
                    "ply", "obj", "stl" -> BasicButton("View model", Modifier.basicButton()) {
                        val intent = Intent(context, OpenGLES20Activity::class.java)
                        intent.putExtra("url", taskDB.value.url)
                        intent.putExtra("type", taskDB.value.file_type)
                        context.startActivity(intent)
                    }

                    else -> TextCard("I don't know how to draw this file", Modifier.fieldModifier())
                }
            } else {
                TextCard("There is nothing to draw in this task", Modifier.fieldModifier())
            }
        }
    }
}

