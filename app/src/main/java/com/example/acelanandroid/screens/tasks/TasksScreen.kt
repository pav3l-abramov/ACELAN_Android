package com.example.acelanandroid.screens.tasks

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.acelanandroid.OPEN_TASK_SCREEN
import com.example.acelanandroid.common.composable.BasicButton
import com.example.acelanandroid.common.composable.RegularCardEditor
import com.example.acelanandroid.common.ext.basicButton
import com.example.acelanandroid.common.ext.card
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.retrofit.data.Task
import com.example.acelanandroid.screens.profile.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TasksScreen(
    navController:NavController,
    modifier: Modifier = Modifier,
    loginViewModel: LoginViewModel = hiltViewModel(),
    tasksViewModel: TasksViewModel = hiltViewModel()
) {
    val uiStateUser by loginViewModel.uiStateUser
    val coroutineScope = rememberCoroutineScope()
    coroutineScope.launch {
        Log.d("tasks", "startMain1")
        loginViewModel.checkUser()
    }
    val dataList: List<Task> by tasksViewModel.dataList.collectAsState()

    if (!uiStateUser.isActive ) {
        Column(        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            Text(text = "Go to profile and login")
        }

    } else {
        coroutineScope.launch {
            tasksViewModel.getToken()
        }
        coroutineScope.launch {
            tasksViewModel.getListTasks()
        }
        LazyColumn {
            itemsIndexed(items = dataList) { index, item ->
                RegularCardEditor(item.name, Modifier.fieldModifier()
                ) { navController.navigate(route = OPEN_TASK_SCREEN+"/${item.id}") }
            }
        }
    }
}
