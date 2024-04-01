package com.example.acelanandroid.screens.materials

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.acelanandroid.common.composable.BasicButton
import com.example.acelanandroid.common.composable.GraphSettingBoolean
import com.example.acelanandroid.common.composable.GraphSettingColor
import com.example.acelanandroid.common.composable.GraphSettingDivide
import com.example.acelanandroid.common.composable.MaterialCardMain
import com.example.acelanandroid.common.composable.SaveButton
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
    navController: NavController,
    context: Context
) {
    val userDB by mainViewModel.userDB.collectAsState()
    val settingCheck by graphSettingViewModel.settingCheck.collectAsState()
    val settingGraphTypeXLabel by graphSettingViewModel.settingGraphTypeXLabel.collectAsState()
    val settingGraphLineShow by graphSettingViewModel.settingGraphLineShow.collectAsState()
    val settingGraphColorLine by graphSettingViewModel.settingGraphColorLine.collectAsState()
    val settingGraphColorPoint by graphSettingViewModel.settingGraphColorPoint.collectAsState()
    val settingGraphDivideFactorStiffness by graphSettingViewModel.settingGraphDivideFactorStiffness.collectAsState()
    val settingGraphDivideFactorPiezo by graphSettingViewModel.settingGraphDivideFactorPiezo.collectAsState()
    val settingGraphDivideFactorDielectric by graphSettingViewModel.settingGraphDivideFactorDielectric.collectAsState()
    val settingGraphDivideFactorYoung by graphSettingViewModel.settingGraphDivideFactorYoung.collectAsState()
    val settingGraphDivideFactorPoison by graphSettingViewModel.settingGraphDivideFactorPoison.collectAsState()

    LaunchedEffect(key1 = null) {
        graphSettingViewModel.setStartSetting(userDB)
    }

    val listParamBoolean = listOf(
        GraphSettingList("Show name material on graph", settingGraphTypeXLabel,0),
        GraphSettingList("Show line", settingGraphLineShow,1),
    )
    val listParamColor = listOf(
        GraphSettingList("Color line", settingGraphColorLine,0),
        GraphSettingList("Color point", settingGraphColorPoint,1),
)
    val listParamDivide = listOf(
        GraphSettingDivideList("Divide factor stiffness", settingGraphDivideFactorStiffness,0),
        GraphSettingDivideList("Divide factor piezomodul", settingGraphDivideFactorPiezo,1),
        GraphSettingDivideList("Divide factor dielectric", settingGraphDivideFactorDielectric,2),
        GraphSettingDivideList("Divide factor poison", settingGraphDivideFactorYoung,3),
        GraphSettingDivideList("Divide factor young modulus", settingGraphDivideFactorPoison,4),
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
                    },
                    actions = {
                        if (settingCheck) {
                            SaveButton(
                                onCancelMain = {

                                        try {
                                            CoroutineScope(Job()).launch {
                                                mainViewModel.updateGraphSetting(
                                                    settingGraphTypeXLabel,
                                                    settingGraphLineShow,
                                                    settingGraphColorLine,
                                                    settingGraphColorPoint,
                                                    settingGraphDivideFactorStiffness.toInt(),
                                                    settingGraphDivideFactorPiezo.toInt(),
                                                    settingGraphDivideFactorDielectric.toInt(),
                                                    settingGraphDivideFactorYoung.toInt(),
                                                    settingGraphDivideFactorPoison.toInt(),
                                                    1
                                                )
                                            }
                                            Toast.makeText(
                                                context,
                                                "Save",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        } catch (e: NumberFormatException) {
                                            Toast.makeText(
                                                context,
                                                "Error",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }

                                },
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
                            item(span = StaggeredGridItemSpan.FullLine){ Spacer(modifier = Modifier.size(8.dp))}
                            items(listParamBoolean) { item ->
                                GraphSettingBoolean(
                                    content = item.nameParam,
                                    valueParam = item.valueParam,
                                    numParam = item.numParam,
                                    onEditClick = graphSettingViewModel::onBooleanChange,
                                    //onEditClick = {},
                                    modifier = Modifier.fieldModifier()
                                )
                            }
                            items(listParamColor) { item ->
                                GraphSettingColor(
                                    content = item.nameParam,
                                    valueParam = item.valueParam,
                                    numParam = item.numParam,
                                    onEditClick = graphSettingViewModel::onColorChange,
                                    modifier = Modifier.fieldModifier()
                                )
                            }
                            items(listParamDivide) { item ->
                                GraphSettingDivide(
                                    content = item.nameParam,
                                    valueParam = item.valueParam,
                                    numParam = item.numParam,
                                    onEditClick = graphSettingViewModel::onDivideChange,
                                    modifier = Modifier.fieldModifier()
                                )
                            }
                        }
                    )
                }

                else -> {
                    LazyColumn {
                        item{ Spacer(modifier = Modifier.size(8.dp))}
                        items(listParamBoolean) { item ->
                            GraphSettingBoolean(
                                content = item.nameParam,
                                valueParam = item.valueParam,
                                numParam = item.numParam,
                                onEditClick = graphSettingViewModel::onBooleanChange,
                               // onEditClick = {},
                                modifier = Modifier.fieldModifier()
                            )
                        }
                        items(listParamColor) { item ->
                            GraphSettingColor(
                                content = item.nameParam,
                                valueParam = item.valueParam,
                                numParam = item.numParam,
                                onEditClick = graphSettingViewModel::onColorChange,
                                modifier = Modifier.fieldModifier()
                            )
                        }
                        items(listParamDivide) { item ->
                            GraphSettingDivide(
                                content = item.nameParam,
                                valueParam = item.valueParam,
                                numParam = item.numParam,
                                onEditClick = graphSettingViewModel::onDivideChange,
                                modifier = Modifier.fieldModifier()
                            )
                        }
                    }
                    }
                }
            }
        }
    }

