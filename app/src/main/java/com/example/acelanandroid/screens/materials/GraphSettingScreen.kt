package com.example.acelanandroid.screens.materials

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.acelanandroid.GRAPH_SETTING_SCREEN
import com.example.acelanandroid.OPEN_MATERIAL_SCREEN
import com.example.acelanandroid.common.composable.BasicButton
import com.example.acelanandroid.common.composable.GraphSettingBoolean
import com.example.acelanandroid.common.composable.GraphSettingColor
import com.example.acelanandroid.common.composable.GraphSettingDivide
import com.example.acelanandroid.common.composable.MaterialCardMain
import com.example.acelanandroid.common.composable.SettingButton
import com.example.acelanandroid.common.ext.fieldModifier
import com.example.acelanandroid.screens.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GraphSettingScreen(
    mainViewModel: MainViewModel= hiltViewModel(),
    graphSettingViewModel: GraphSettingViewModel= hiltViewModel(),
    navController: NavController
) {
    val userDB by mainViewModel.userDB.collectAsState()
    val settingGraphTypeXLabel by graphSettingViewModel.settingGraphTypeXLabel.collectAsState()
    val settingGraphLineShow by graphSettingViewModel.settingGraphLineShow.collectAsState()
    val settingGraphColorLine by graphSettingViewModel.settingGraphColorLine.collectAsState()
    val settingGraphColorPoint by graphSettingViewModel.settingGraphColorPoint.collectAsState()
    val settingGraphDivideFactorStiffness by graphSettingViewModel.settingGraphDivideFactorStiffness.collectAsState()
    val settingGraphDivideFactorPiezo by graphSettingViewModel.settingGraphDivideFactorPiezo.collectAsState()
    val settingGraphDivideFactorDielectric by graphSettingViewModel.settingGraphDivideFactorDielectric.collectAsState()
    val settingGraphDivideFactorYoung by graphSettingViewModel.settingGraphDivideFactorYoung.collectAsState()
    val settingGraphDivideFactorPoison by graphSettingViewModel.settingGraphDivideFactorPoison.collectAsState()

    val listParamBoolean = listOf(
        GraphSettingList("Show name material on graph", userDB.graphTypeXLabel),
        GraphSettingList("Show line", userDB.graphLineShow),
    )
    val listParamColor = listOf(
        GraphSettingList("Color line", userDB.graphColorLine),
        GraphSettingList("Color point", userDB.graphColorPoint),
)
    val listParamDivide = listOf(
        GraphSettingList("Divide factor stiffness", userDB.graphDivideFactorStiffness),
        GraphSettingList("Divide factor piezomodul", userDB.graphDivideFactorPiezo),
        GraphSettingList("Divide factor dielectric", userDB.graphDivideFactorDielectric),
        GraphSettingList("Divide factor poison", userDB.graphDivideFactorPoison),
        GraphSettingList("Divide factor young modulus", userDB.graphDivideFactorYoung),
    )
    val state = rememberLazyStaggeredGridState()
    val configuration = LocalConfiguration.current

    Scaffold(
        topBar = {

                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(
                            "Graphic Setting",
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
                    }
                )

        },
        floatingActionButton = {}
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            when (configuration.orientation) {
                Configuration.ORIENTATION_LANDSCAPE -> {
                    LazyVerticalStaggeredGrid(
                        columns = StaggeredGridCells.Fixed(2),
                        modifier = Modifier.fillMaxSize(),
                        state = state,
                        content = {
                            items(listParamBoolean) { item ->
                                GraphSettingBoolean(content = item.nameParam, valueParam = item.valueParam)
                            }
                            items(listParamColor) { item ->
                                GraphSettingColor(content = item.nameParam, valueParam = item.valueParam)
                            }
                            items(listParamDivide) { item ->
                                GraphSettingDivide(content = item.nameParam, valueParam = item.valueParam)
                            }
                            item(span = StaggeredGridItemSpan.FullLine){
                                BasicButton(text = "Save setting", modifier = Modifier.fieldModifier()) {
                                    CoroutineScope(Job()).launch {
                                        mainViewModel.updateGraphSetting(
                                            settingGraphTypeXLabel,
                                            settingGraphLineShow,
                                            settingGraphColorLine,
                                            settingGraphColorPoint,
                                            settingGraphDivideFactorStiffness,
                                            settingGraphDivideFactorPiezo,
                                            settingGraphDivideFactorDielectric,
                                            settingGraphDivideFactorYoung,
                                            settingGraphDivideFactorPoison,
                                            1
                                        )

                                }
                            }
                                }
                        }
                    )
                }

                else -> {
                    LazyColumn {
                        items(listParamBoolean) { item ->
                            GraphSettingBoolean(content = item.nameParam, valueParam = item.valueParam)
                        }
                        items(listParamColor) { item ->
                            GraphSettingColor(content = item.nameParam, valueParam = item.valueParam)
                        }
                        items(listParamDivide) { item ->
                            GraphSettingDivide(content = item.nameParam, valueParam = item.valueParam)
                        }
                        item{ BasicButton(text = "Save setting", modifier = Modifier.fieldModifier()) {
                            CoroutineScope(Job()).launch {
                                mainViewModel.updateGraphSetting(
                                    settingGraphTypeXLabel,
                                    settingGraphLineShow,
                                    settingGraphColorLine,
                                    settingGraphColorPoint,
                                    settingGraphDivideFactorStiffness,
                                    settingGraphDivideFactorPiezo,
                                    settingGraphDivideFactorDielectric,
                                    settingGraphDivideFactorYoung,
                                    settingGraphDivideFactorPoison,
                                    1
                                )

                            }
                        }}
                    }
                }
            }
        }
    }
}
