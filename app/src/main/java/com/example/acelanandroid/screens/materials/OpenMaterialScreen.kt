package com.example.acelanandroid.screens.materials

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.acelanandroid.GRAPH_SCREEN
import com.example.acelanandroid.common.composable.CustomLinearProgressBar
import com.example.acelanandroid.common.composable.FABOpenMaterialComposable
import com.example.acelanandroid.common.composable.MaterialDetailCard
import com.example.acelanandroid.common.composable.TextCardStandart
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.data.MaterialToDraw
import com.example.acelanandroid.retrofit.GetStateMaterialDetail
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
fun OpenMaterialScreen(
    navController: NavController,
    idMaterial: Int,
    modifier: Modifier = Modifier,
    openMaterialViewModel: OpenMaterialViewModel = hiltViewModel(),
    context: Context,
    mainViewModel: MainViewModel = hiltViewModel(),
) {
    val materialDetailDB by mainViewModel.materialDetailDB.collectAsState()
    val materialGraphDB by mainViewModel.materialGraphDB.collectAsState()
    val userDB by mainViewModel.userDB
    val checkUser by mainViewModel.checkUser
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val uiCheckStatus by openMaterialViewModel.uiCheckStatus
    val isLoading by openMaterialViewModel.isLoading.collectAsState()
    val isMainFABOpen = remember { mutableStateOf(false) }
    val isShowButton = remember { mutableStateOf(true) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    LaunchedEffect(Unit) {
        mainViewModel.userIsExist()
        mainViewModel.getUserDB()

    }
    CoroutineScope(Job()).launch {
        mainViewModel.getMaterialByID(idMaterial)
        mainViewModel.updateMaterialGraph()
    }
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
        LaunchedEffect(materialDetailDB) {
            if (materialDetailDB.young == null && materialDetailDB.id != null) {
                openMaterialViewModel.getListMaterialDetailWithRetry(
                    userDB.token.toString(),
                    idMaterial,
                    context
                )
                mainViewModel.getMaterialByID(idMaterial)
            }
            isMainFABOpen.value = materialGraphDB.contains(MaterialToDraw(idMaterial))
            Log.d("sssssssssssssssssss", materialGraphDB.toString())
//            if (materialGraphDB.contains(idMaterial)){}
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
                            "Detail Material",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
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
                    actions = {
                        Switch(
                            checked = isShowButton.value,
                            onCheckedChange = {
                                isShowButton.value = it
                            }
                        )
                    },

                    )
            },
            floatingActionButton = {
                if (isShowButton.value) {

                    FABOpenMaterialComposable(
                        mainButtonOn = isMainFABOpen.value,
                        onCancelMain = {
                            if (!isMainFABOpen.value) {
                                CoroutineScope(Job()).launch {
                                    mainViewModel.insertMaterialToDraw(MaterialToDraw(idMaterial))
                                    isMainFABOpen.value = true
                                }
                            } else navController.navigate(route = GRAPH_SCREEN)
                        },
                        onDelete = {
                            CoroutineScope(Job()).launch {
                                mainViewModel.deleteMaterialToDraw(idMaterial)
                                isMainFABOpen.value = false
                            }
                        })
                }
            },
            content = {

                SwipeRefresh(modifier = Modifier.padding(it), state = swipeRefreshState,
                    onRefresh = {
                        openMaterialViewModel.getListMaterialDetailWithRetry(
                            userDB.token.toString(),
                            idMaterial,
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
                                materialDetailDB.name.toString(),
                                false,
                                Modifier.fieldModifier()
                            ),
                            DataDetailCard(
                                "Type: ",
                                materialDetailDB.type.toString(),
                                false,
                                Modifier.fieldModifier()
                            ),
                            DataDetailCard(
                                "Create: ",
                                materialDetailDB.created_at.toString(),
                                true,
                                Modifier.fieldModifier()
                            ),
                            DataDetailCard(
                                "Update: ",
                                materialDetailDB.updated_at.toString(),
                                true,
                                Modifier.fieldModifier()
                            ),
                            DataDetailCard(
                                "Core: ",
                                materialDetailDB.core.toString(),
                                false,
                                Modifier.fieldModifier()
                            ),
                            DataDetailCard(
                                "Source: ",
                                materialDetailDB.source.toString(),
                                false,
                                Modifier.fieldModifier()
                            ),
                            DataDetailCard(
                                "Young Modulus: ",
                                materialDetailDB.young.toString(),
                                false,
                                Modifier.fieldModifier()
                            ),
                            DataDetailCard(
                                "Poison Coefficient: ",
                                materialDetailDB.poison.toString(),
                                false,
                                Modifier.fieldModifier()
                            ),
                        )
                        listDetail.forEach { detail ->
                            MaterialDetailCard(
                                detail.content,
                                detail.materialData,
                                detail.checkTime,
                                detail.modifier
                            )
                            if (detail.content == "Source: ") {
                                TextCardStandart("Properties", Modifier.fieldModifier())
                            }
                        }
                        if (isLoading) {
                            CustomLinearProgressBar(Modifier.fieldModifier())
                        }

                    }
                }

            })
    }
    openMaterialViewModel.materialDetailState.observe(lifecycleOwner) { state ->
        Log.d("start", state.toString())
        when (state) {
            GetStateMaterialDetail.Loading -> {
                Log.d("Loading", state.toString())
            }

            is GetStateMaterialDetail.Success -> {
                CoroutineScope(Job()).launch {
                    mainViewModel.handleSuccessStateOpenMaterialScreen(state)
                }

            }

            is GetStateMaterialDetail.Error -> {
                mainViewModel.handleErrorStateOpenMaterialScreen(state)
                Log.d("Error", state.toString())
                val error = state.error
                openMaterialViewModel.typeError(error)
            }
        }
    }


}