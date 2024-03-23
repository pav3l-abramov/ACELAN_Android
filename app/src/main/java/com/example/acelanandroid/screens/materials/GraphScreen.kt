package com.example.acelanandroid.screens.materials

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.acelanandroid.GRAPH_SCREEN
import com.example.acelanandroid.common.composable.FABOpenMaterialComposable
import com.example.acelanandroid.common.composable.PointChart
import com.example.acelanandroid.common.composable.TextCardStandart
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.screens.FilterViewModel
import com.example.acelanandroid.screens.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@Composable
fun GraphScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel = hiltViewModel(),
    graphViewModel: GraphViewModel = hiltViewModel(),
    context: Context
) {
    val materialListDraw by mainViewModel.materialListDraw.collectAsState()

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            mainViewModel.getDataToGraph()
        }
    }
    val scrollState = rememberLazyListState()
    val heightTopBar = 56.dp
    Log.d("2materialGraphDB.size.toString()",materialListDraw.toString())
    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = !(scrollState.firstVisibleItemIndex > 0 ||scrollState.firstVisibleItemScrollOffset>0) ,
                enter = slideInVertically(),
                exit = slideOutVertically()
            ) {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Graphic",
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
                //actions = {}
            )
        }
        },
        floatingActionButton = {}
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val listGraphMaterial = listOf(
                materialListDraw.poisonList?.let { it1 ->
                    materialListDraw.nameList?.let { it2 ->
                        GraphMaterialData(
                            it1,
                            "μ",
                            "Poison Coefficient",
                            it2
                        )
                    }
                },
                materialListDraw.youngList?.let { it1 ->
                    materialListDraw.nameList?.let { it2 ->
                        GraphMaterialData(
                            it1,
                            "E, 10⁹ N/m²",
                            "Young Modulus",
                            it2
                        )
                    }
                }
            )
            Log.d("4materialGraphDB.size.toString()",materialListDraw.nameList?.size.toString())

            when (materialListDraw.nameList?.size) {

                0,null ->
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextCardStandart(
                            "Nothing to show. Add 2 materials to draw graph",
                            Modifier.fieldModifier()
                        )
                        Log.d("6materialGraphDB.size.toString()",materialListDraw.toString())
                    }


                1 ->
                    Column(
                        modifier = modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextCardStandart("Add another material", Modifier.fieldModifier())
                        Log.d("8materialGraphDB.size.toString()",materialListDraw.toString())
                    }

                else -> {
                    Log.d("10materialGraphDB.size.toString()",materialListDraw.toString())
                    LazyColumn(state = scrollState, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        items(listGraphMaterial) { data ->
                            if (data != null) {
                                TextCardStandart(data.paramName, Modifier.fieldModifier())
                                PointChart(
                                    120.dp,
                                    listOf(),
                                    data.yValues,
                                    "name material",
                                    data.yLabelName,
                                    true,
                                    data.materialNameList
                                )
                            }
                        }

                    }
//                    listGraphMaterial.forEach { data ->
//                        if (data != null) {
//                            //TextCardStandart(data.paramName, Modifier.fieldModifier())
//                            PointChart(
//                                120.dp,
//                                listOf(),
//                                data.yValues,
//                                "name material",
//                                data.yLabelName,
//                                true,
//                                data.materialNameList
//                            )
//                        }
//                    }
                }
            }
        }

    }
}

