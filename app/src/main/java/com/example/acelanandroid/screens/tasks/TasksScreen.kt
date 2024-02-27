package com.example.acelanandroid.screens.tasks

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import com.example.acelanandroid.OPEN_TASK_SCREEN
import com.example.acelanandroid.common.composable.CustomLinearProgressBar
import com.example.acelanandroid.common.composable.TaskCard
import com.example.acelanandroid.common.composable.TextCardStandart
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.data.TaskMain
import com.example.acelanandroid.data.UserData
import com.example.acelanandroid.data.singleData.Task
import com.example.acelanandroid.retrofit.GetStateTasks
import com.example.acelanandroid.retrofit.PostState
import com.example.acelanandroid.screens.MainViewModel
import com.example.acelanandroid.screens.profile.LoginViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TasksScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    tasksViewModel: TasksViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
    context: Context
) {

    val tasksList = mainViewModel.taskListDB.collectAsState(initial = emptyList())
    val userDB by mainViewModel.userDB
    val checkUser by mainViewModel.checkUser
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val uiCheckStatus by tasksViewModel.uiCheckStatus


    LaunchedEffect(Unit) {
            mainViewModel.userIsExist()
        }


    Log.d("checkUsercheckUsercheckUser", checkUser.toString())
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
            mainViewModel.getUserDB()
            tasksViewModel.getListTasksWithRetry(userDB.token.toString())
        }
        when (uiCheckStatus.status) {
            null -> {}
            else -> {
                Toast.makeText(
                    context,
                    uiCheckStatus.body.toString(),
                    Toast.LENGTH_SHORT
                ).show()
                tasksViewModel.nullStatus()
            }
        }
        tasksViewModel.tasksState.observe(lifecycleOwner) { state ->
            Log.d("start", state.toString())
            when (state) {
                GetStateTasks.Loading -> {
                    Log.d("Loading", state.toString())
                }

                is GetStateTasks.Success -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        Log.d("Success2", state.toString())
                        val tasks =state.tasks.tasks
                        tasks.forEach() {
                            mainViewModel.insertTaskToDB(TaskMain(it.id))
                                mainViewModel.updateTaskMain(
                                    it.name, it.status, it.started_at, it.finished_at,
                                    it.id
                                )
                        }
                    }

                }

                is GetStateTasks.Error -> {
                    Log.d("Error", state.toString())
                    val error = state.error
                    tasksViewModel.typeError(error)
                }
            }
        }
        LazyColumn {
            items(tasksList.value) { item ->
                item.name?.let {
                    item.status?.let { it1 ->
                        TaskCard(
                            it, Modifier.fieldModifier(), it1
                        ) { navController.navigate(route = OPEN_TASK_SCREEN + "/${item.id}") }
                    }
                }
            }
        }
    }

}

