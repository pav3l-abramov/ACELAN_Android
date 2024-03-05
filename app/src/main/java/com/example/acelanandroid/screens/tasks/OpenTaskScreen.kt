package com.example.acelanandroid.screens.tasks

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.acelanandroid.common.composable.BasicButton
import com.example.acelanandroid.common.composable.TaskDetailCard
import com.example.acelanandroid.common.composable.TextCard
import com.example.acelanandroid.common.ext.basicButton
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.OpenGLES20Activity
import com.example.acelanandroid.common.composable.CustomLinearProgressBar
import com.example.acelanandroid.common.composable.FABOpenMaterialComposable
import com.example.acelanandroid.common.composable.FABTaskDrawComposable
import com.example.acelanandroid.common.composable.MaterialDetailCard
import com.example.acelanandroid.common.composable.PointChart
import com.example.acelanandroid.common.composable.TextCardStandart
import com.example.acelanandroid.data.TaskMain
import com.example.acelanandroid.data.UserData
import com.example.acelanandroid.retrofit.GetStateTaskDetail
import com.example.acelanandroid.retrofit.GetStateTasks
import com.example.acelanandroid.screens.DataDetailCard
import com.example.acelanandroid.screens.MainViewModel
import com.example.acelanandroid.screens.profile.LoginViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


@OptIn(DelicateCoroutinesApi::class, ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun OpenTaskScreen(
    modifier: Modifier = Modifier,
    openTaskViewModel: OpenTaskViewModel = hiltViewModel(),
    idTask: Int,
    context: Context,
    mainViewModel: MainViewModel = hiltViewModel(),
    navController: NavController
) {
    val taskDB by mainViewModel.taskDetailDB.collectAsState()
    val userDB by mainViewModel.userDB
    val checkUser by mainViewModel.checkUser
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val uiCheckStatus by openTaskViewModel.uiCheckStatus
    val isLoading by openTaskViewModel.isLoading.collectAsState()
    val isDraw = remember { mutableStateOf(false) }
    val isGraph = remember { mutableStateOf(false) }
    val isShowButton = remember { mutableStateOf(true) }
    val isShowGraphOnScreen = remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    LaunchedEffect(Unit) {
        mainViewModel.userIsExist()
        mainViewModel.getUserDB()
    }
    CoroutineScope(Job()).launch { mainViewModel.getTaskByID(idTask) }
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

        LaunchedEffect(taskDB) {
            if ((taskDB.url == null && taskDB.x.isNullOrEmpty() && taskDB.id != null)) {
                openTaskViewModel.getListTaskDetailWithRetry(
                    userDB.token.toString(),
                    idTask,
                    context
                )
                mainViewModel.getTaskByID(idTask)
            }
        }

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(
                            "Detail Task",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    actions = {
                        Switch(
                            checked = isShowButton.value,
                            onCheckedChange = {
                                isShowButton.value = it
                            }
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Localized description"
                            )
                        }
                    },
                )
            },
            floatingActionButton = {
                if (isShowButton.value) {
                    FABTaskDrawComposable(
                        isDraw = isDraw.value,
                        isGraph = isGraph.value,
                        drawModel = {
                            val intent = Intent(context, OpenGLES20Activity::class.java)
                            intent.putExtra("url", taskDB.url)
                            intent.putExtra("type", taskDB.file_type)
                            context.startActivity(intent)
                        },
                        drawGraph = { isShowGraphOnScreen.value = !isShowGraphOnScreen.value })
                }

            },
            content = {
                SwipeRefresh(modifier = Modifier.padding(it), state = swipeRefreshState,
                    onRefresh = {
                        openTaskViewModel.getListTaskDetailWithRetry(
                            userDB.token.toString(),
                            idTask,
                            context
                        )
                    }) {
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        val listDetail = listOf(
                            DataDetailCard(
                                "Name: ",
                                taskDB.name.toString(),
                                false,
                                Modifier.fieldModifier()
                            ),
                            DataDetailCard(
                                "Status: ",
                                taskDB.status.toString(),
                                false,
                                Modifier.fieldModifier()
                            ),
                            DataDetailCard(
                                "Start: ",
                                taskDB.started_at.toString(),
                                true,
                                Modifier.fieldModifier()
                            ),
                            DataDetailCard(
                                "Finish: ",
                                taskDB.finished_at.toString(),
                                true,
                                Modifier.fieldModifier()
                            ),
                            DataDetailCard(
                                "File type: ",
                                taskDB.file_type.toString(),
                                false,
                                Modifier.fieldModifier()
                            ),
                            DataDetailCard(
                                "File url: ",
                                taskDB.url.toString(),
                                false,
                                Modifier.fieldModifier()
                            ),
                        )
                        listDetail.forEach { detail ->
                            TaskDetailCard(
                                detail.content,
                                detail.materialData,
                                detail.checkTime,
                                detail.modifier
                            )
                        }
                        if (isLoading) {
                            CustomLinearProgressBar(Modifier.fieldModifier())
                        }
                        if (taskDB.url != null) {
                            when (taskDB.file_type) {
                                "jpg", "png" -> DrawImage(taskDB.url!!, Modifier.fieldModifier())
                                "ply", "obj", "stl" -> isDraw.value = true
                                else -> TextCard(
                                    "I don't know how to draw this file",
                                    Modifier.fieldModifier()
                                )
                            }
                        }
//                        if (!taskDB.x.isNullOrEmpty()) {
                        isGraph.value = true
                        if (isShowGraphOnScreen.value) {
                            PointChart(
                                120.dp,
                                listOf(-1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f,8.0f,15.0f,16.0f,17.0f,18.0f),
                                listOf(0.0f, 2.0f, 30.0f, 100.0f, 50.0f, 40.0f, 20.0f, 24.0f, 16.0f, 8.0f, 4.0f),
                                "x",
                                "y"
                            )
                            //fun to draw by x/y
                            //DrawGraph(x = taskDB.x!!, y = taskDB.y!!, colorBackground = MaterialTheme.colorScheme.background, modifier =Modifier.fieldModifier() )
                        }
                        //   }

                        //DrawGraph(x = listOf(1.0f,2.0f,5.0f), y = listOf(1.0f,2.0f,5.0f), colorBackground = MaterialTheme.colorScheme.background, modifier =Modifier.fieldModifier() )


                        if (taskDB.url == null && taskDB.graph_type == null) {
                            TextCard(
                                "There is nothing to draw in this task",
                                Modifier.fieldModifier()
                            )
                        }
                    }
                }
            })


        //        when (uiCheckStatus.status) {
//            null -> {}
//            else -> {
//                Toast.makeText(
//                    context,
//                    uiCheckStatus.body.toString(),
//                    Toast.LENGTH_SHORT
//                ).show()
//                openTaskViewModel.nullStatus()
//            }
//        }
        openTaskViewModel.taskDetailState.observe(lifecycleOwner) { state ->
            Log.d("start", state.toString())
            when (state) {
                GetStateTaskDetail.Loading -> {
                    Log.d("Loading", state.toString())
                }

                is GetStateTaskDetail.Success -> {
                    CoroutineScope(Job()).launch {
                        mainViewModel.handleSuccessStateOpenTaskScreen(state)
                    }

                }

                is GetStateTaskDetail.Error -> {
                    mainViewModel.handleErrorStateOpenTaskScreen(state)
                    Log.d("Error", state.toString())
                    val error = state.error
                    openTaskViewModel.typeError(error)
                }
            }
        }
    }
}


