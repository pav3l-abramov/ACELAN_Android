package com.example.acelanandroid.screens.tasks

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.acelanandroid.OPEN_TASK_SCREEN
import com.example.acelanandroid.common.composable.TaskCard
import com.example.acelanandroid.common.composable.TextCardStandart
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.data.TaskMain
import com.example.acelanandroid.data.UserData
import com.example.acelanandroid.data.singleData.Task
import com.example.acelanandroid.screens.MainViewModel
import com.example.acelanandroid.screens.profile.LoginViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@OptIn(DelicateCoroutinesApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun TasksScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    tasksViewModel: TasksViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel()
) {

    val tasksList = mainViewModel.taskListDB.collectAsState(initial = emptyList())
    val userDB = mainViewModel.getUserDB.collectAsState(initial = UserData())
    val checkUser by mainViewModel.checkUser
    LaunchedEffect(Unit) {
    GlobalScope.async {
        mainViewModel.userIsExist()
    }
}
    val dataList: List<TaskMain> by tasksViewModel.dataList.collectAsState()
    Log.d("checkUsercheckUsercheckUser",checkUser.toString())
    if (!checkUser) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            TextCardStandart("Go to profile and login", Modifier.fieldModifier())
        }

    } else {
        LaunchedEffect(Unit) {
            GlobalScope.async {
                if (checkUser) {
                    userDB.value.token?.let { tasksViewModel.getListTasks(it) }
                    for (task in dataList){
                        mainViewModel.insertTaskToDB(task)
                        task.id?.let {
                            mainViewModel.updateTaskMain(task.name,task.status,task.started_at,task.finished_at,
                                it
                            )
                        }
                    }
                    //tasksList=dataList
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

