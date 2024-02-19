package com.example.acelanandroid.screens.tasks

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.acelanandroid.OPEN_TASK_SCREEN
import com.example.acelanandroid.common.composable.TaskCard
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.retrofit.data.Task
import com.example.acelanandroid.screens.profile.LoginViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TasksScreen(
    navController:NavController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    tasksViewModel: TasksViewModel = hiltViewModel()
) {
    val uiStateUser by loginViewModel.uiStateUser
    LaunchedEffect(Unit) {
        GlobalScope.launch {
            loginViewModel.checkUser()
        }
    }
    val dataList: List<Task> by tasksViewModel.dataList.collectAsState()

    if (!uiStateUser.isActive ) {
        Column(modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "Go to profile and login")
        }

    } else {


        LaunchedEffect(Unit) {
            GlobalScope.launch {
                if (uiStateUser.isActive) {
                    tasksViewModel.getListTasks(uiStateUser.token)
                }
            }
        }
        LazyColumn {
            itemsIndexed(items = dataList) { index, item ->
                TaskCard(item.name, Modifier.fieldModifier(),item.status
                ) { navController.navigate(route = OPEN_TASK_SCREEN+"/${item.id}") }
            }
        }
    }
}

