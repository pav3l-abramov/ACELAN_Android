package com.example.acelanandroid.screens.tasks

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.acelanandroid.OPEN_TASK_SCREEN
import com.example.acelanandroid.common.composable.CustomLinearProgressBar
import com.example.acelanandroid.common.composable.TaskCard
import com.example.acelanandroid.common.composable.TextCardStandart
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.retrofit.GetStateTasks
import com.example.acelanandroid.screens.MainViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition", "UnrememberedMutableState")
@Composable
fun TasksScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    tasksViewModel: TasksViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
    context: Context
) {

    val tasksList by mainViewModel.taskListDB.collectAsState()
    val userDB by mainViewModel.userDB
    val checkUser by mainViewModel.checkUser
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val uiCheckStatus by tasksViewModel.uiCheckStatus
    val isLoading by tasksViewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    LaunchedEffect(Unit) {
        mainViewModel.userIsExist()
        mainViewModel.getUserDB()

    }
    CoroutineScope(Job()).launch { mainViewModel.updateTaskList()}
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
        LaunchedEffect(tasksList) {

            if (tasksList.isEmpty()) {
                Log.d("tasksListtasksListtasksListtasksListtasksList", tasksList.toString())
                tasksViewModel.getListTasksWithRetry(userDB.token.toString(),context)
                mainViewModel.updateTaskList()
            }

        }


        if (tasksList.isEmpty()) {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextCardStandart("Download data...", Modifier.fieldModifier())
                CustomLinearProgressBar(Modifier.fieldModifier())
            }
        }
        else {
            SwipeRefresh(state = swipeRefreshState,
                onRefresh = {
                    tasksViewModel.getListTasksWithRetry(
                        userDB.token.toString(),
                        context
                    )
                }) {
                LazyColumn {
                    items(tasksList) { item ->
                        item.name?.let {
                            item.status?.let { it1 ->
                                TaskCard(
                                    it, Modifier.fieldModifier(), it1
                                ) {
                                    navController.navigate(route = OPEN_TASK_SCREEN + "/${item.id}")
                                }
                            }
                        }
                    }
                }

            }
        }
    }
    tasksViewModel.tasksState.observe(lifecycleOwner) { state ->
        Log.d("start", state.toString())
        when (state) {
            GetStateTasks.Loading -> {
                Log.d("Loading", state.toString())
            }

            is GetStateTasks.Success -> {

                CoroutineScope(Job()).launch {
//                    if (tasksList.value.size != state.tasks.tasks.size &&
//                        tasksList.value.size > state.tasks.tasks.size
//                    ) {
//                        for (i in 1..tasksList.value.size - state.tasks.tasks.size) mainViewModel.deleteTaskDB(
//                            TaskMain(id = tasksList.value.size-i)
//                        )
//                    }
                    mainViewModel.handleSuccessStateTasksScreen(state)

                }
            }

            is GetStateTasks.Error -> {
                mainViewModel.handleErrorStateTasksScreen(state)
                    val error = state.error
                    tasksViewModel.typeError(error)
            }
        }
    }
//            when (uiCheckStatus.status) {
//            null -> {}
//            else -> {
//                Toast.makeText(
//                    context,
//                    uiCheckStatus.body.toString(),
//                    Toast.LENGTH_SHORT
//                ).show()
//                tasksViewModel.nullStatus()
//            }
//        }

}

