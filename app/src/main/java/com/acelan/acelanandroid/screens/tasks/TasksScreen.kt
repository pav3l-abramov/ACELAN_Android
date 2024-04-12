package com.acelan.acelanandroid.screens.tasks

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.acelan.acelanandroid.OPEN_TASK_SCREEN
import com.acelan.acelanandroid.common.composable.CustomLinearProgressBar
import com.acelan.acelanandroid.common.composable.FABTaskComposable
import com.acelan.acelanandroid.common.composable.FilterDialogTask
import com.acelan.acelanandroid.common.composable.TaskCard
import com.acelan.acelanandroid.common.composable.TextCardStandart
import com.acelan.acelanandroid.common.ext.fieldModifier
import com.acelan.acelanandroid.retrofit.GetStateTasks
import com.acelan.acelanandroid.screens.FilterViewModel
import com.acelan.acelanandroid.screens.MainViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint(
    "CoroutineCreationDuringComposition", "UnrememberedMutableState",
    "UnusedMaterial3ScaffoldPaddingParameter"
)
@Composable
fun TasksScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    tasksViewModel: TasksViewModel = hiltViewModel(),
    mainViewModel: MainViewModel = hiltViewModel(),
    context: Context,
    filterViewModel: FilterViewModel = hiltViewModel()
) {
    val tasksList by mainViewModel.taskListDB.collectAsState()
    val userDB by mainViewModel.userDB.collectAsState()
    val checkUser by mainViewModel.checkUser.collectAsState()
    val checkOpenTaskScreen by mainViewModel.checkOpenTaskScreen.collectAsState()
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val uiCheckStatus by tasksViewModel.uiCheckStatus
    val isLoading by tasksViewModel.isLoading.collectAsState()
    val isDialogOpen = remember { mutableStateOf(false) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    val uiStateFilter by filterViewModel.uiStateFilter
    val uiStateSorted by filterViewModel.uiStateSorted
    val sortedTaskListDB by filterViewModel.sortedTaskListDB.collectAsState()
    val state = rememberLazyStaggeredGridState()


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
        LaunchedEffect(tasksList) {
            withContext(Dispatchers.IO) {
                if (tasksList.isEmpty()|| !checkOpenTaskScreen) {
                    tasksViewModel.getListTasksWithRetry(userDB.token.toString(), context)
                    mainViewModel.isOpenTaskScreen()
                }
            }
        }

        if (tasksList.isEmpty() && isLoading) {
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
        } else {
            Scaffold(
                floatingActionButton = {
                    FABTaskComposable(
                        onCancelFilter = { isDialogOpen.value = true })
                }
            ) {
                SwipeRefresh(state = swipeRefreshState,
                    onRefresh = {
                        tasksViewModel.getListTasksWithRetry(
                            userDB.token.toString(),
                            context
                        )
                    }) {
                    filterViewModel.onSortedTaskMain(tasksList)

                    val configuration = LocalConfiguration.current

                    when (configuration.orientation) {
                        Configuration.ORIENTATION_LANDSCAPE -> {
                            LazyVerticalStaggeredGrid(
                                columns = StaggeredGridCells.Fixed(2),
                                modifier = Modifier.fillMaxSize(),
                                state = state,
                                content = {
                                    items(sortedTaskListDB) { item ->
                                        if (item.status == uiStateFilter.filterStatusTask || uiStateFilter.filterStatusTask == "All") {
                                            TaskCard(
                                                item.name.toString(),
                                                Modifier.fieldModifier(),
                                                item.status.toString(),
                                                item.started_at.toString(),
                                                item.finished_at.toString()
                                            ) {
                                                navController.navigate(route = OPEN_TASK_SCREEN + "/${item.id}")
                                            }
                                        }
                                    }
                                }
                            )
                        }

                        else -> {
                            LazyColumn {
                                items(sortedTaskListDB) { item ->
                                    if (item.status == uiStateFilter.filterStatusTask || uiStateFilter.filterStatusTask == "All") {
                                        TaskCard(
                                            item.name.toString(),
                                            Modifier.fieldModifier(),
                                            item.status.toString(),
                                            item.started_at.toString(),
                                            item.finished_at.toString()
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
            if (isDialogOpen.value) {
                FilterDialogTask(
                    filterText = "Filter",
                    sortedText = "Sorted",
                    status = uiStateFilter.filterStatusTask,
                    sortedParam = uiStateSorted.sortedBy,
                    onNewValueStatusFilter = filterViewModel::onNewValueStatusFilter,
                    onNewValueSortedParam = filterViewModel::onNewSortedParam,
                    modifier = Modifier.fieldModifier(),
                    onCancel = { isDialogOpen.value = false },
                    color = MaterialTheme.colorScheme.background
                )
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

            else -> {}
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

