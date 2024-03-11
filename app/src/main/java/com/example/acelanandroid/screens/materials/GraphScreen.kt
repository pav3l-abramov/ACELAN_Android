package com.example.acelanandroid.screens.materials

import android.annotation.SuppressLint
import android.content.Context
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
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.data.MaterialToDraw
import com.example.acelanandroid.screens.FilterViewModel
import com.example.acelanandroid.screens.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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
    CoroutineScope(Job()).launch {
        mainViewModel.updateMaterialGraph()
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
            Column(modifier = Modifier.padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally) {

//                val listGraphMaterial = listOf(
//                    GraphMaterialData())

                when(materialGraphDB.size){
                    0->  Text(text = "Nothing to show. Add 2 materials to draw graph.")
                    1-> Text(text = "Add another material.")
                    else ->{
                        PointChart(
                            120.dp,
                            listOf(),
                            listOf(0.0f, 2.0f, 30.0f, 100.0f, 50.0f, 40.0f, 20.0f, 24.0f, 16.0f, 8.0f, 4.0f),
                            "name material",
                            "name parameter, divide factor",
                            true,
                            listOf("pzt1", "pzt2", "pzt3", "pzt4", "pzt5", "pzt6")
                        )

                    }
                }
            }

        })
}

