package com.example.acelanandroid.screens.materials

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.acelanandroid.OPEN_MATERIAL_SCREEN
import com.example.acelanandroid.common.composable.CustomLinearProgressBar
import com.example.acelanandroid.common.composable.MaterialCard
import com.example.acelanandroid.common.composable.TextCardStandart
import com.example.acelanandroid.common.ext.fieldModifier
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.res.painterResource
import com.example.acelanandroid.R
import com.example.acelanandroid.common.composable.FABMaterialComposable
import com.example.acelanandroid.common.composable.FilterDialogMaterial
import com.example.acelanandroid.retrofit.GetStateMaterial
import com.example.acelanandroid.screens.FilterViewModel
import com.example.acelanandroid.screens.MainViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaterialsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel(),
    materialViewModel: MatrialViewModel = hiltViewModel(),
    filterViewModel: FilterViewModel = hiltViewModel(),
    context: Context
) {
    val uiStateFilter by filterViewModel.uiStateFilter
    val materialsList by mainViewModel.materialListDB.collectAsState()
    val materialToSearch by mainViewModel.materialToSearch.collectAsState()
    val userDB by mainViewModel.userDB
    val checkUser by mainViewModel.checkUser
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val isLoading by materialViewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    val isActiveSearch = remember { mutableStateOf(false) }
    val isDialogOpen = remember { mutableStateOf(false) }
    val isMainFABOpen = remember { mutableStateOf(false) }
    val isListEmpty = remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            mainViewModel.userIsExist()
            mainViewModel.getUserDB()
        }
    }
    CoroutineScope(Job()).launch { mainViewModel.updateMaterialList() }
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
        LaunchedEffect(materialsList) {
            withContext(Dispatchers.IO) {
                if (materialsList.isEmpty()) {
                    Log.d("tasksListtasksListtasksListtasksListtasksList", materialsList.toString())
                    materialViewModel.getListMaterialsWithRetry(
                        "",
                        userDB.token.toString(),
                        context,
                        false
                    )
                    mainViewModel.updateMaterialList()
                }
            }
        }
        if (materialsList.isEmpty()) {
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
                        FABMaterialComposable(
                            mainButtonOn = isMainFABOpen.value,
                            onCancelMain = { isMainFABOpen.value=!isMainFABOpen.value },
                            onCancelFilter = { isDialogOpen.value =true },
                            onCancelGraph = {isDialogOpen.value =true})

                },
                content = {
                    SwipeRefresh(state = swipeRefreshState,
                        onRefresh = {
                            materialViewModel.getListMaterialsWithRetry(
                                "",
                                userDB.token.toString(),
                                context,
                                false
                            )
                        }) {
                        Column(
                            modifier = modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Top,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            val searchText = remember { mutableStateOf("") }
                            SearchBar(
                                modifier = Modifier.fieldModifier(),
                                query = searchText.value,
                                onQueryChange = { text ->
                                    searchText.value = text
                                    if (searchText.value == "") {
                                        materialViewModel.getListMaterialsWithRetry(
                                            searchText.value,
                                            userDB.token.toString(),
                                            context,
                                            true
                                        )
                                    }
                                },
                                onSearch = {
                                    Log.d("searchText.value", searchText.value)
                                    materialViewModel.getListMaterialsWithRetry(
                                        searchText.value,
                                        userDB.token.toString(),
                                        context,
                                        true
                                    )

                                },
                                placeholder = {
                                    Text(text = "Search...")
                                },
                                active = isActiveSearch.value,
                                onActiveChange = {
                                    isActiveSearch.value = it

                                }) {
                                LazyColumn {
                                    items(materialToSearch) { item ->
                                        MaterialCard(
                                            content = item.name, modifier = Modifier.fieldModifier()
                                        ) { navController.navigate(route = OPEN_MATERIAL_SCREEN + "/${item.id}") }
                                    }
                                }
                            }

                            LazyColumn {
                                isListEmpty.value=true
                                items(materialsList) { item ->
                                    if (filterViewModel.checkCoreFilter(
                                            item,
                                            uiStateFilter.filterCore
                                        ) && filterViewModel.checkTypeFilter(
                                            item,
                                            uiStateFilter.filterType
                                        )
                                    ) {
                                        isListEmpty.value=false
                                        item.name?.let {
                                            MaterialCard(
                                                content = it,
                                                modifier = Modifier.fieldModifier()
                                            ) { navController.navigate(route = OPEN_MATERIAL_SCREEN + "/${item.id}") }

                                        }
                                    }
                                }
                            }

                            if (isDialogOpen.value) {
                                FilterDialogMaterial(
                                    message = "Filter",
//                                    youngMin = uiStateFilter.filterYoungMin,
//                                    youngMax = uiStateFilter.filterYoungMax,
//                                    youngOn = uiStateFilter.filterYoungOn,
                                    core = uiStateFilter.filterCore,
                                    type = uiStateFilter.filterType,
//                                    onNewValueMainYoungFilter = {
//                                        filterViewModel.onNewValueMainYoungFilter(
//                                            !uiStateFilter.filterYoungOn
//                                        )
//                                    },
                                    onNewValueCoreFilter = filterViewModel::onCoreFilterChange,
                                    onNewValueTypeFilter = filterViewModel::onTypeFilterChange,
//                                    onNewValueYoungMinFilter = filterViewModel::onYoungMinFilterChange,
//                                    onNewValueYoungMaxFilter = filterViewModel::onYoungMaxFilterChange,
                                    onCancel = { isDialogOpen.value = false },
                                    modifier = Modifier.fieldModifier(),
                                    color = MaterialTheme.colorScheme.background
                                )
                            }
//                                                        if (isListEmpty.value) {
////                                Column(
////                                    modifier = modifier
////                                        .fillMaxSize()
////                                        .verticalScroll(rememberScrollState()),
////                                    verticalArrangement = Arrangement.Center,
////                                    horizontalAlignment = Alignment.CenterHorizontally
////                                ) {
////                                    TextCardStandart("No matches", Modifier.fieldModifier())
////                                }
//
//                            }

                        }
                    }
                }
            )

        }
    }
    materialViewModel.materialsState.observe(lifecycleOwner) { state ->
        Log.d("start", state.toString())
        when (state) {
            GetStateMaterial.Loading -> {
                Log.d("Loading", state.toString())
            }

            is GetStateMaterial.Success -> {

                CoroutineScope(Job()).launch {
//                    if (tasksList.value.size != state.tasks.tasks.size &&
//                        tasksList.value.size > state.tasks.tasks.size
//                    ) {
//                        for (i in 1..tasksList.value.size - state.tasks.tasks.size) mainViewModel.deleteTaskDB(
//                            TaskMain(id = tasksList.value.size-i)
//                        )
//                    }
                    mainViewModel.handleSuccessStateMaterialScreen(state)

                }
            }

            is GetStateMaterial.Error -> {
                mainViewModel.handleErrorStateMaterialsScreen(state)
                val error = state.error
                materialViewModel.typeError(error)
            }
        }
    }
}

