package com.example.acelanandroid.screens.materials

import android.annotation.SuppressLint
import android.content.Context
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.example.acelanandroid.GRAPH_SCREEN
import com.example.acelanandroid.OPEN_MATERIAL_SCREEN
import com.example.acelanandroid.common.composable.CustomLinearProgressBar
import com.example.acelanandroid.common.composable.FABOpenMaterialComposable
import com.example.acelanandroid.common.composable.InterfaceButton
import com.example.acelanandroid.common.composable.MaterialDetailCard
import com.example.acelanandroid.common.composable.DrawTable
import com.example.acelanandroid.common.composable.MaterialCardMain
import com.example.acelanandroid.common.composable.TextCardStandart
import com.example.acelanandroid.common.ext.fieldModifier
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
@SuppressLint("CoroutineCreationDuringComposition", "UnusedMaterial3ScaffoldPaddingParameter",
    "SuspiciousIndentation"
)
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
    val userDB by mainViewModel.userDB.collectAsState()
    val checkUser by mainViewModel.checkUser.collectAsState()
    val lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current
    val uiCheckStatus by openMaterialViewModel.uiCheckStatus
    val isLoading by openMaterialViewModel.isLoading.collectAsState()
   // val isMainFABOpen = remember { mutableStateOf(false) }
    val isShowButton = remember { mutableStateOf(true) }
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    val state = rememberLazyStaggeredGridState()

    CoroutineScope(Job()).launch { mainViewModel.getMaterialByID(idMaterial) }

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
        LaunchedEffect(materialDetailDB) {
            if (materialDetailDB.young == null && materialDetailDB.id != null) {
                openMaterialViewModel.getListMaterialDetailWithRetry(
                    userDB.token.toString(),
                    idMaterial,
                    context
                )
                mainViewModel.getMaterialByID(idMaterial)
            }
            //isMainFABOpen.value = materialGraphDB.contains(MaterialToDraw(idMaterial))
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
                        InterfaceButton(
                            mainButtonOn = isShowButton.value,
                            onCancelMain = {isShowButton.value = !isShowButton.value}
                        )
                    },

                    )
            },
            floatingActionButton = {
                if (isShowButton.value) {

                    FABOpenMaterialComposable(
                        mainButtonOn = materialDetailDB.isDraw==1,
                        onCancelMain = {
                            if (materialDetailDB.isDraw==0) {
                                CoroutineScope(Job()).launch {
                                   // mainViewModel.insertMaterialToDraw(MaterialToDraw(idMaterial))
                                    mainViewModel.updateMaterialCardDraw(true,idMaterial)
                                    mainViewModel.getMaterialByID(idMaterial)
                                }
                            } else navController.navigate(route = GRAPH_SCREEN)
                        },
                        onDelete = {
                            CoroutineScope(Job()).launch {
                               // mainViewModel.deleteMaterialToDraw(idMaterial)
                                mainViewModel.updateMaterialCardDraw(false,idMaterial)
                                mainViewModel.getMaterialByID(idMaterial)
                            }
                        },
                        color = MaterialTheme.colorScheme.background)
                }
            }
        ) {

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
                       ,
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

                    )
                    val listOnlyIsotropic = listOf(
                        DataDetailCard(
                            "Young Modulus, N/m²: ",
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
                        DataDetailCard(
                            "d, C/N:",
                            "not exist",
                            false,
                            Modifier.fieldModifier(),
                        ),
                        DataDetailCard(
                            "ε, F/m∙ε₀:",
                            materialDetailDB.dielectricScalar.toString(),
                            false,
                            Modifier.fieldModifier(),
                        ),
                    )

                    val paramIsotropicToTable = listOf(
                        materialDetailDB.stiffness?.let { it1 ->
                            TableList(
                                param = "C",
                                row = 1,
                                col = 2,
                                item = it1,
                                maxItemsInRow = 2,
                                description = "Elastic Properties: ",
                                dimension = "10⁹ N/m²"
                            )
                        }
                    )
                    val paramAnisotropicToTable = listOf(
                        materialDetailDB.piezo?.let { it1 ->
                            TableList(
                                param = "d",
                                row = 3,
                                col = 6,
                                item = it1,
                                maxItemsInRow = 6,
                                description = "Piezoelectric Properties:",
                                dimension = "C/N"
                            )
                        },
                        materialDetailDB.stiffness?.let { it1 ->
                            TableList(
                                param = "C",
                                row = 6,
                                col = 6,
                                item = it1,
                                maxItemsInRow = 6,
                                description = "Elastic Properties: ",
                                dimension = "10⁹ N/m²"
                            )
                        },
                        materialDetailDB.dielectric?.let { it1 ->
                            TableList(
                                param = "ε",
                                row = 3,
                                col = 3,
                                item = it1,
                                maxItemsInRow = 3,
                                description = "Dielectric: ",
                                dimension = "F/m∙ε₀"
                            )
                        }
                    )

                    val configuration = LocalConfiguration.current



                    when (configuration.orientation) {
                        Configuration.ORIENTATION_LANDSCAPE -> {
                                LazyVerticalStaggeredGrid(
                                    columns = StaggeredGridCells.Fixed(2),
                                    modifier = Modifier.fillMaxSize(),
                                    state = state,
                                    content = {
                                        items(listDetail) { detail ->
                                            MaterialDetailCard(
                                                detail.content,
                                                detail.materialData,
                                                detail.checkTime,
                                                detail.modifier,
                                                false
                                            )
                                        }
                                        if (isLoading) {
                                            item(span = StaggeredGridItemSpan.FullLine) {
                                                CustomLinearProgressBar(Modifier.fieldModifier())
                                            }
                                        }
                                        if (materialDetailDB.type?.contains("Isotropic") == true) {
                                            item(span = StaggeredGridItemSpan.FullLine) {
                                                TextCardStandart("Properties", Modifier.fieldModifier())
                                            }
                                            items(listOnlyIsotropic){ detail ->
                                                MaterialDetailCard(
                                                    detail.content,
                                                    detail.materialData,
                                                    detail.checkTime,
                                                    detail.modifier,
                                                    true
                                                )
                                            }
                                            items(paramIsotropicToTable){ data ->
                                                if (data != null) {
                                                    DrawTable(
                                                        param = data.param,
                                                        row = data.row,
                                                        col = data.col,
                                                        item = data.item,
                                                        maxItemsInRow = data.maxItemsInRow,
                                                        modifier = Modifier.fieldModifier(),
                                                        description = data.description,
                                                        dimension = data.dimension
                                                    )
                                                }
                                            }
                                        }
                                        if (materialDetailDB.type?.contains("Anisotropic") == true) {
                                            items(paramAnisotropicToTable){ data ->
                                                if (data != null) {
                                                    DrawTable(
                                                        param = data.param,
                                                        row = data.row,
                                                        col = data.col,
                                                        item = data.item,
                                                        maxItemsInRow = data.maxItemsInRow,
                                                        modifier = Modifier.fieldModifier(),
                                                        description = data.description,
                                                        dimension = data.dimension
                                                    )
                                                }
                                            }
                                        }
                                    }
                                )
                            }

                        else -> {
                            LazyColumn {
                                items(listDetail){ detail ->
                                    MaterialDetailCard(
                                        detail.content,
                                        detail.materialData,
                                        detail.checkTime,
                                        detail.modifier,
                                        false
                                    )
                                }
                                if (isLoading) {
                                    item{CustomLinearProgressBar(Modifier.fieldModifier())}
                                }
                                if (materialDetailDB.type?.contains("Isotropic") == true) {
                                    item { TextCardStandart("Properties", Modifier.fieldModifier()) }
                                    items(listOnlyIsotropic){ detail ->
                                        MaterialDetailCard(
                                            detail.content,
                                            detail.materialData,
                                            detail.checkTime,
                                            detail.modifier,
                                            true
                                        )
                                    }
                                    items(paramIsotropicToTable){ data ->
                                        if (data != null) {
                                            DrawTable(
                                                param = data.param,
                                                row = data.row,
                                                col = data.col,
                                                item = data.item,
                                                maxItemsInRow = data.maxItemsInRow,
                                                modifier = Modifier.fieldModifier(),
                                                description = data.description,
                                                dimension = data.dimension
                                            )
                                        }
                                    }
                                }
                                if (materialDetailDB.type?.contains("Anisotropic") == true) {
                                    items(paramAnisotropicToTable){ data ->
                                        if (data != null) {
                                            DrawTable(
                                                param = data.param,
                                                row = data.row,
                                                col = data.col,
                                                item = data.item,
                                                maxItemsInRow = data.maxItemsInRow,
                                                modifier = Modifier.fieldModifier(),
                                                description = data.description,
                                                dimension = data.dimension
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
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