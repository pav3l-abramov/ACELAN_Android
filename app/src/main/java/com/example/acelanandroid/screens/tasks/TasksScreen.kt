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
import com.example.acelanandroid.common.composable.BasicButton
import com.example.acelanandroid.common.composable.RegularCardEditor
import com.example.acelanandroid.common.ext.basicButton
import com.example.acelanandroid.common.ext.card
import com.example.acelanandroid.retrofit.data.Task
import com.example.acelanandroid.screens.profile.LoginViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TasksScreen(
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
    val uiState by tasksViewModel.uiStateCheck

    if (!uiStateUser.isActive ) {
        Text(text = "not Nice")
    } else {
        coroutineScope.launch {
            Log.d("tasks", "startMain1")
            tasksViewModel.getToken()
        }
        coroutineScope.launch {
            Log.d("tasks", "startMain2")
            tasksViewModel.getListTasks()
        }
        LazyColumn {
            itemsIndexed(items = dataList) { index, item ->
                Text(
                    text = "Item ${index + 1}: ${item.name}",
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}
