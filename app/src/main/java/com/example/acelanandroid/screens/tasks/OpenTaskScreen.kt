package com.example.acelanandroid.screens.tasks

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.acelanandroid.common.composable.TaskDetailCard
import com.example.acelanandroid.common.composable.TextCard
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.OpenGLES20Activity
import com.example.acelanandroid.common.composable.CustomLinearProgressBar
import com.example.acelanandroid.common.composable.DrawTable
import com.example.acelanandroid.common.composable.FABTaskDrawComposable
import com.example.acelanandroid.common.composable.InterfaceButton
import com.example.acelanandroid.common.composable.MaterialDetailCard
import com.example.acelanandroid.common.composable.PointChart
import com.example.acelanandroid.common.composable.TextCardStandart
import com.example.acelanandroid.retrofit.GetStateTaskDetail
import com.example.acelanandroid.screens.DataDetailCard
import com.example.acelanandroid.screens.MainViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Job
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
    val userDB by mainViewModel.userDB.collectAsState()
    val checkUser by mainViewModel.checkUser.collectAsState()
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val uiCheckStatus by openTaskViewModel.uiCheckStatus
    val isLoading by openTaskViewModel.isLoading.collectAsState()
    val isDraw = remember { mutableStateOf(false) }
    val isGraph = remember { mutableStateOf(false) }
    val isShowButton = remember { mutableStateOf(true) }
    val isShowGraphOnScreen = remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    val state = rememberLazyStaggeredGridState()

    CoroutineScope(Job()).launch { mainViewModel.getTaskByID(idTask) }
    if (checkUser) {
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
                        InterfaceButton(
                            mainButtonOn = isShowButton.value,
                            onCancelMain = {isShowButton.value=!isShowButton.value}

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
                    val listDetail = listOf(
                        DataDetailCard("Name: ",taskDB.name.toString(),false,Modifier.fieldModifier()),
                        DataDetailCard("Status: ",taskDB.status.toString(),false,Modifier.fieldModifier()),
                        DataDetailCard("Start: ",taskDB.started_at.toString(),true,Modifier.fieldModifier()),
                        DataDetailCard("Finish: ",taskDB.finished_at.toString(),true,Modifier.fieldModifier()),
                        DataDetailCard(if (isGraph.value) "Graph type:" else "File type: ",if (isGraph.value) taskDB.graph_type.toString() else taskDB.file_type.toString(), false, Modifier.fieldModifier()),
                        DataDetailCard(if (isGraph.value) "Count point:" else "File url: ",if (isGraph.value) taskDB.x?.size.toString() else taskDB.url.toString(), false, Modifier.fieldModifier()),
                    )
                    val configuration = LocalConfiguration.current
                    isDraw.value=false
                    when (configuration.orientation) {
                        Configuration.ORIENTATION_LANDSCAPE -> {
                            LazyVerticalStaggeredGrid(
                                columns = StaggeredGridCells.Fixed(2),
                                modifier = Modifier.fillMaxSize(),
                                state = state,
                                content = {
                                    items(listDetail) { detail ->
                                        TaskDetailCard(
                                            detail.content,
                                            detail.materialData,
                                            detail.checkTime,
                                            detail.modifier,
                                        )
                                    }
                                    if (isLoading) {
                                        item(span = StaggeredGridItemSpan.FullLine) {
                                            CustomLinearProgressBar(Modifier.fieldModifier())
                                        }
                                    }

                                    if (taskDB.url != null) {
                                        when (taskDB.file_type) {
                                            "jpg", "png","webm" -> item(span = StaggeredGridItemSpan.FullLine){DrawImage(taskDB.url!!, Modifier.fieldModifier())}
                                            "ply", "obj", "stl" -> isDraw.value = true
                                            else ->
                                                item(span = StaggeredGridItemSpan.FullLine) {
                                                    TextCard(
                                                        "I don't know how to draw this file",
                                                        Modifier.fieldModifier()
                                                    )
                                                }
                                        }
                                    }
                                    if (taskDB.graph_type=="2DGraph") {
                                        isGraph.value = true
                                        if (isShowGraphOnScreen.value) {
                                            item(span = StaggeredGridItemSpan.FullLine){
                                                taskDB.x?.let { it1 ->
                                                    taskDB.y?.let { it2 ->
                                                        PointChart(
                                                            120.dp,
                                                            it1,
                                                            it2,
                                                            "x",
                                                            "y",
                                                            false,
                                                            listOf()
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (taskDB.url == null && taskDB.graph_type == null) {
                                        item(span = StaggeredGridItemSpan.FullLine) {
                                            TextCard(
                                                "There is nothing to draw in this task",
                                                Modifier.fieldModifier()
                                            )
                                        }
                                    }

                                }
                            )
                        }

                        else -> {
                            LazyColumn {
                                items(listDetail){ detail ->
                                    TaskDetailCard(
                                        detail.content,
                                        detail.materialData,
                                        detail.checkTime,
                                        detail.modifier,
                                    )
                                }
                                if (isLoading) {
                                    item{CustomLinearProgressBar(Modifier.fieldModifier())}
                                }
                                if (taskDB.url != null) {
                                    when (taskDB.file_type) {
                                        "jpg", "png","webm" -> item{DrawImage(taskDB.url!!, Modifier.fieldModifier())}
                                        "ply", "obj", "stl" -> isDraw.value = true
                                        else ->
                                            item {
                                                TextCard(
                                                    "I don't know how to draw this file",
                                                    Modifier.fieldModifier()
                                                )
                                            }
                                    }
                                }
                                if (taskDB.graph_type=="2DGraph") {
                                    isGraph.value = true
                                    if (isShowGraphOnScreen.value) {
                                        item{
                                        taskDB.x?.let { it1 ->
                                            taskDB.y?.let { it2 ->
                                                PointChart(
                                                    120.dp,
                                                    it1,
                                                    it2,
                                                    "x",
                                                    "y",
                                                    false,
                                                    listOf()
                                                )
                                            }
                                        }
                                        }
                                    }
                                }
                                if (taskDB.url == null && taskDB.graph_type == null) {
                                    item {
                                        TextCard(
                                            "There is nothing to draw in this task",
                                            Modifier.fieldModifier()
                                        )
                                    }
                                }



                            }
                        }
                    }




//                    Column(
//                        modifier = modifier
//                            .fillMaxSize()
//                            .verticalScroll(rememberScrollState()),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//
////                        if (taskDB.url != null) {
////                            when (taskDB.file_type) {
////                                "jpg", "png","webm" -> DrawImage(taskDB.url!!, Modifier.fieldModifier())
////                                "ply", "obj", "stl" -> isDraw.value = true
////                                else -> TextCard(
////                                    "I don't know how to draw this file",
////                                    Modifier.fieldModifier()
////                                )
////                            }
////                        }
////                        if (taskDB.graph_type=="2DGraph") {
////                        isGraph.value = true
////                        if (isShowGraphOnScreen.value) {
////                            taskDB.x?.let { it1 ->
////                                taskDB.y?.let { it2 ->
////                                    PointChart(
////                                        120.dp,
////                                        it1,
////                                        it2,
////                                        "x",
////                                        "y",
////                                        false,
////                                        listOf()
////                                    )
////                                }
////                            }
////                        }
////                           }
//
//                        //DrawGraph(x = listOf(1.0f,2.0f,5.0f), y = listOf(1.0f,2.0f,5.0f), colorBackground = MaterialTheme.colorScheme.background, modifier =Modifier.fieldModifier() )
//
//
//                        if (taskDB.url == null && taskDB.graph_type == null) {
//                            TextCard(
//                                "There is nothing to draw in this task",
//                                Modifier.fieldModifier()
//                            )
//                        }
//                    }
                }
            }

        )


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


