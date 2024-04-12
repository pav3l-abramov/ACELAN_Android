package com.acelan.acelanandroid.screens.materials

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.acelan.acelanandroid.OPEN_MATERIAL_SCREEN
import com.acelan.acelanandroid.common.composable.CustomLinearProgressBar
import com.acelan.acelanandroid.common.composable.MaterialCard
import com.acelan.acelanandroid.common.composable.TextCardStandart
import com.acelan.acelanandroid.common.ext.fieldModifier
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.platform.LocalConfiguration
import com.acelan.acelanandroid.GRAPH_SCREEN
import com.acelan.acelanandroid.common.composable.FABMaterialComposable
import com.acelan.acelanandroid.common.composable.FilterDialogMaterial
import com.acelan.acelanandroid.common.composable.MaterialCardMain
import com.acelan.acelanandroid.retrofit.GetStateMaterial
import com.acelan.acelanandroid.screens.FilterViewModel
import com.acelan.acelanandroid.screens.MainViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter",
    "SuspiciousIndentation"
)
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
    val userDB by mainViewModel.userDB.collectAsState()
    val checkUser by mainViewModel.checkUser.collectAsState()
    val checkOpenMaterialScreen by mainViewModel.checkOpenMaterialScreen.collectAsState()
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val isLoading by materialViewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    val isActiveSearch = remember { mutableStateOf(false) }
    val isDialogOpen = remember { mutableStateOf(false) }
    val isMainFABOpen = remember { mutableStateOf(false) }
    val isListEmpty = remember { mutableStateOf(false) }
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
        LaunchedEffect(materialsList) {
            withContext(Dispatchers.IO) {
                if (materialsList.isEmpty()|| !checkOpenMaterialScreen) {
                    materialViewModel.getListMaterialsWithRetry(
                        "",
                        userDB.token.toString(),
                        context,
                        false
                    )
                    mainViewModel.isOpenMaterialScreen()
                }
            }
        }
        if (materialsList.isEmpty()&&isLoading) {
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
                            onCancelGraph = {navController.navigate(route = GRAPH_SCREEN)},
                            color = MaterialTheme.colorScheme.background)

                }
            ) {
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


                        val configuration = LocalConfiguration.current

                        when (configuration.orientation) {
                            Configuration.ORIENTATION_LANDSCAPE -> {
                                LazyVerticalStaggeredGrid(
                                    columns = StaggeredGridCells.Fixed(2),
                                    modifier = Modifier.fillMaxSize(),
                                    state = state,
                                    content = {
                                        items(materialsList) { item ->
                                            if (filterViewModel.checkCoreFilter(
                                                    item,
                                                    uiStateFilter.filterCore
                                                ) && filterViewModel.checkTypeFilter(
                                                    item,
                                                    uiStateFilter.filterType
                                                )
                                            ) {

                                                MaterialCardMain(
                                                    content = item.name.toString(),
                                                    typeMaterial = item.type.toString(),
                                                    isDraw = item.isDraw == 1,
                                                    onEditClick = { navController.navigate(route = OPEN_MATERIAL_SCREEN + "/${item.id}") },
                                                    modifier = Modifier.fieldModifier()
                                                )
                                            }
                                        }
                                    }
                                )
                            }

                            else -> {
                                LazyColumn {
                                    items(materialsList) { item ->
                                        if (filterViewModel.checkCoreFilter(
                                                item,
                                                uiStateFilter.filterCore
                                            ) && filterViewModel.checkTypeFilter(
                                                item,
                                                uiStateFilter.filterType
                                            )
                                        ) {
                                            MaterialCardMain(
                                                content = item.name.toString(),
                                                typeMaterial = item.type.toString(),
                                                isDraw = item.isDraw == 1,
                                                onEditClick = { navController.navigate(route = OPEN_MATERIAL_SCREEN + "/${item.id}") },
                                                modifier = Modifier.fieldModifier()
                                            )
                                        }
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

            else -> {}
        }
    }
}

