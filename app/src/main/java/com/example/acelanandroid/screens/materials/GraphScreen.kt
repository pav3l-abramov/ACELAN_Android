package com.example.acelanandroid.screens.materials

import android.annotation.SuppressLint
import android.content.Context
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
import com.example.acelanandroid.data.MaterialToDraw
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
    val materialGraphDB by mainViewModel.materialGraphDB.collectAsState()
    val materialListDraw by mainViewModel.materialListDraw.collectAsState()

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            mainViewModel.updateMaterialGraph()
            mainViewModel.getDataToGraph()
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
                actions = {}
            )
        },
        floatingActionButton = {},
        content = {
            Column(modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally) {

                val listGraphMaterial = listOf(
                    materialListDraw.poisonList?.let { it1 -> materialListDraw.nameList?.let { it2 ->GraphMaterialData(it1,"poison",it2)}},
                    materialListDraw.youngList?.let { it1 -> materialListDraw.nameList?.let { it2 ->GraphMaterialData(it1,"young, 10^9",it2)}}
                )
                when(materialGraphDB.size){
                    0->
                        Column(
                        modifier = modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextCardStandart("Nothing to show. Add 2 materials to draw graph", Modifier.fieldModifier())
                    }
                    1->
                        Column(
                            modifier = modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            TextCardStandart("Add another material", Modifier.fieldModifier())
                        }
                    else ->{
                        listGraphMaterial.forEach{data->
                            if (data != null) {
                                TextCardStandart(data.yLabelName, Modifier.fieldModifier())

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
                }
            }

        })
}

