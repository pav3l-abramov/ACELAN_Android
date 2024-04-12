package com.acelan.acelanandroid.screens.materials

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.acelan.acelanandroid.GRAPH_SETTING_SCREEN
import com.acelan.acelanandroid.common.composable.DrawTableToGraph
import com.acelan.acelanandroid.common.composable.MaterialDetailCard
import com.acelan.acelanandroid.common.composable.PointChart
import com.acelan.acelanandroid.common.composable.SettingButton
import com.acelan.acelanandroid.common.composable.TextCardStandart
import com.acelan.acelanandroid.common.composable.TextCardWithSubstring
import com.acelan.acelanandroid.common.composable.TextGraphMaterialType
import com.acelan.acelanandroid.common.ext.fieldModifier
import com.acelan.acelanandroid.screens.MainViewModel
import kotlinx.coroutines.Dispatchers
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
    val userDB by mainViewModel.userDB.collectAsState()
    val materialIsotropicListDraw by mainViewModel.materialIsotropicListDraw.collectAsState()
    val materialAnisotropicListDraw by mainViewModel.materialAnisotropicListDraw.collectAsState()
    val elasticPropertiesList by graphViewModel.elasticPropertiesList.collectAsState()
    val piezoelectricPropertiesList by graphViewModel.piezoelectricPropertiesList.collectAsState()
    val dielectricPropertiesList by graphViewModel.dielectricPropertiesList.collectAsState()

    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            mainViewModel.getDataToGraph(userDB)
        }
    }
    val scrollState = rememberLazyListState()
    val heightTopBar = 56.dp
    Log.d("2materialGraphDB.size.toString()", materialIsotropicListDraw.toString())
    Scaffold(
        topBar = {
            AnimatedVisibility(
                visible = !(scrollState.firstVisibleItemIndex > 0 || scrollState.firstVisibleItemScrollOffset > 0),
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
                    actions = {
                        SettingButton(
                            onCancelMain = { navController.navigate(route = GRAPH_SETTING_SCREEN) }
                        )
                    },
                )
            }
        },
        floatingActionButton = {}
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            val listGraphMaterialIsotropic = listOf(
                materialIsotropicListDraw.poisonList?.let { it1 ->
                    materialIsotropicListDraw.nameList?.let { it2 ->
                        GraphMaterialData(
                            it1,
                            graphViewModel.getYTitle("μ",userDB.graphDivideFactorPoison.toString(),"")
                            ,
                            "Poison Coefficient",
                            it2
                        )
                    }
                },
                materialIsotropicListDraw.youngList?.let { it1 ->
                    materialIsotropicListDraw.nameList?.let { it2 ->
                        GraphMaterialData(
                            it1,
                            graphViewModel.getYTitle("E",userDB.graphDivideFactorYoung.toString(),"N/m²")
                            ,
                            "Young Modulus",
                            it2
                        )
                    }
                }
            )

            Log.d(
                "4materialGraphDB.size.toString()",
                materialIsotropicListDraw.nameList?.size.toString()
            )

//            when (materialIsotropicListDraw.nameList?.size) {
//
//                0,null ->
//                    Column(
//                        modifier = modifier
//                            .fillMaxSize()
//                            .verticalScroll(rememberScrollState()),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        TextCardStandart(
//                            "Nothing to show. Add 2 materials to draw graph",
//                            Modifier.fieldModifier()
//                        )
//                        Log.d("6materialGraphDB.size.toString()",materialIsotropicListDraw.toString())
//                    }
//
//
//                1 ->
//                    Column(
//                        modifier = modifier
//                            .fillMaxSize()
//                            .verticalScroll(rememberScrollState()),
//                        verticalArrangement = Arrangement.Center,
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        TextCardStandart("Add another material", Modifier.fieldModifier())
//                        Log.d("8materialGraphDB.size.toString()",materialIsotropicListDraw.toString())
//                    }
//
//                else -> {
            Log.d("10materialGraphDB.size.toString()", materialIsotropicListDraw.toString())
            LazyColumn(
                state = scrollState,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                item { TextGraphMaterialType("Isotropic materials", Modifier.fieldModifier()) }
                when (materialIsotropicListDraw.nameList?.size) {
                    0, null -> item {
                        TextCardStandart(
                            "Nothing to show. Add 2 materials to draw graph",
                            Modifier.fieldModifier()
                        )
                    }

                    1 -> item { TextCardStandart("Add another material", Modifier.fieldModifier()) }
                    else -> {
                        if (!listGraphMaterialIsotropic[0]?.materialNameList.isNullOrEmpty() && userDB.graphTypeXLabel == 0) {
                            listGraphMaterialIsotropic[0]?.let { it1 ->
                                itemsIndexed(it1.materialNameList) { index, data ->
                                    MaterialDetailCard(
                                        "  ${index + 1}",
                                        data,
                                        false,
                                        Modifier.fieldModifier(),
                                        false
                                    )
                                }
                            }
                        }
                        items(listGraphMaterialIsotropic) { data ->
                            if (data != null) {
                                TextCardStandart(data.paramName, Modifier.fieldModifier())
                                if (data.yValues.isNotEmpty()) {
                                    if (data.yValues.toSet().size > 1) {
                                        PointChart(
                                            120.dp,
                                            listOf(),
                                            data.yValues,
                                            "name material",
                                            data.yLabelName,
                                            true,
                                            data.materialNameList,
                                            ColorProvider.listColor.first { it.numColor == userDB.graphColorLine }.color,
                                            ColorProvider.listColor.first { it.numColor == userDB.graphColorPoint }.color,
                                            userDB.graphLineShow == 1,
                                            userDB.graphTypeXLabel == 1,
                                        )
                                    } else {
                                        TextCardStandart(
                                            "All values are the same.",
                                            Modifier.fieldModifier()
                                        )

                                    }
                                } else {
                                    TextCardStandart(
                                        "Nothing to show. No data",
                                        Modifier.fieldModifier()
                                    )

                                }
                            }
                        }
                    }
                }

                item { TextGraphMaterialType("Anisotropic materials", Modifier.fieldModifier()) }
                when (materialAnisotropicListDraw.nameList?.size) {
                    0, null -> item {
                        TextCardStandart(
                            "Nothing to show. Add 2 materials to draw graph",
                            Modifier.fieldModifier()
                        )
                    }

                    1 -> item { TextCardStandart("Add another material", Modifier.fieldModifier()) }
                    else -> {
                        if (!materialAnisotropicListDraw.nameList.isNullOrEmpty() && userDB.graphTypeXLabel == 0) {
                            itemsIndexed(materialAnisotropicListDraw.nameList!!) { index, data ->
                                MaterialDetailCard(
                                    "  ${index + 1}",
                                    data,
                                    false,
                                    Modifier.fieldModifier(),
                                    false
                                )
                            }
                        }
                        item {
                            DrawTableToGraph(
                                param = "d",
                                row = 3,
                                col = 6,
                                maxItemsInRow = 6,
                                description = "Piezoelectric Properties:",
                                dimension = "C/N",
                                items = piezoelectricPropertiesList,
                                onEditClick = graphViewModel::onEditGraphDraw,
                                modifier = Modifier.fieldModifier()
                            )

                        }
                        items(piezoelectricPropertiesList) { data ->
                            val rowIndex = data / 6
                            val colIndex = data % 6
                            val listPiezo = materialAnisotropicListDraw.piezoList?.let { it1 ->
                                graphViewModel.getParamToList(
                                    it1, data
                                )
                            }
                            TextCardWithSubstring("d", rowIndex, colIndex, Modifier.fieldModifier())

                            if (!listPiezo.isNullOrEmpty()) {
                                if (listPiezo.toSet().size > 1) {
                                    val annotatedString =
                                        graphViewModel.getYTitle("d${graphViewModel.getSubShiftString(rowIndex + 1)}${
                                            graphViewModel.getSubShiftString(
                                                colIndex + 1
                                            )
                                        }",userDB.graphDivideFactorPiezo.toString(),"C/N")


                                    materialAnisotropicListDraw.nameList?.let { it1 ->
                                        PointChart(
                                            120.dp,
                                            listOf(),
                                            listPiezo,
                                            "name material",

                                            annotatedString,
                                            true,
                                            it1,
                                            ColorProvider.listColor.first { it.numColor == userDB.graphColorLine }.color,
                                            ColorProvider.listColor.first { it.numColor == userDB.graphColorPoint }.color,
                                            userDB.graphLineShow == 1,
                                            userDB.graphTypeXLabel == 1,
                                        )
                                    }
                                } else {
                                    TextCardStandart(
                                        "All values are the same.",
                                        Modifier.fieldModifier()
                                    )

                                }
                            } else {
                                TextCardStandart(
                                    "Nothing to show. No data",
                                    Modifier.fieldModifier()
                                )

                            }
                        }
                        item {
                            DrawTableToGraph(
                                param = "C",
                                row = 6,
                                col = 6,
                                maxItemsInRow = 6,
                                description = "Elastic Properties: ",
                                dimension = "10⁹ N/m²",
                                items = elasticPropertiesList,
                                onEditClick = graphViewModel::onEditGraphDraw,
                                modifier = Modifier.fieldModifier()
                            )
                        }
                        items(elasticPropertiesList) { data ->
                            val rowIndex = data / 6
                            val colIndex = data % 6
                            val listElastic =
                                materialAnisotropicListDraw.stiffnessList?.let { it1 ->
                                    graphViewModel.getParamToList(
                                        it1, data
                                    )
                                }
                            TextCardWithSubstring("C", rowIndex, colIndex, Modifier.fieldModifier())

                            if (!listElastic.isNullOrEmpty()) {
                                if (listElastic.toSet().size > 1) {
                                    val annotatedString =
                                        graphViewModel.getYTitle("C${graphViewModel.getSubShiftString(rowIndex + 1)}${
                                            graphViewModel.getSubShiftString(
                                                colIndex + 1
                                            )
                                        }",userDB.graphDivideFactorStiffness.toString(),"N/m²")

                                    materialAnisotropicListDraw.nameList?.let { it1 ->
                                        PointChart(
                                            120.dp,
                                            listOf(),
                                            listElastic,
                                            "name material",
                                            annotatedString,
                                            true,
                                            it1,
                                            ColorProvider.listColor.first { it.numColor == userDB.graphColorLine }.color,
                                            ColorProvider.listColor.first { it.numColor == userDB.graphColorPoint }.color,
                                            userDB.graphLineShow == 1,
                                            userDB.graphTypeXLabel == 1,
                                        )
                                    }
                                } else {
                                    TextCardStandart(
                                        "All values are the same.",
                                        Modifier.fieldModifier()
                                    )

                                }
                            } else {
                                TextCardStandart(
                                    "Nothing to show. No data",
                                    Modifier.fieldModifier()
                                )

                            }
                        }

                        item {
                            DrawTableToGraph(
                                param = "ε",
                                row = 3,
                                col = 3,
                                maxItemsInRow = 3,
                                description = "Dielectric: ",
                                dimension = "F/m∙ε₀",
                                items = dielectricPropertiesList,
                                onEditClick = graphViewModel::onEditGraphDraw,
                                modifier = Modifier.fieldModifier()
                            )
                        }
                        items(dielectricPropertiesList) { data ->
                            val rowIndex = data / 3
                            val colIndex = data % 3
                            val listDielectric =
                                materialAnisotropicListDraw.dielectricList?.let { it1 ->
                                    graphViewModel.getParamToList(
                                        it1, data
                                    )
                                }
                            TextCardWithSubstring("ε", rowIndex, colIndex, Modifier.fieldModifier())

                            if (!listDielectric.isNullOrEmpty()) {
                                if (listDielectric.toSet().size > 1) {
                                    val annotatedString =
                                        graphViewModel.getYTitle("ε${graphViewModel.getSubShiftString(rowIndex + 1)}${
                                            graphViewModel.getSubShiftString(
                                                colIndex + 1
                                            )
                                        }",userDB.graphDivideFactorDielectric.toString(),"F/m∙ε₀")


                                    materialAnisotropicListDraw.nameList?.let { it1 ->
                                        PointChart(
                                            120.dp,
                                            listOf(),
                                            listDielectric,
                                            "name material",
                                            annotatedString,
                                            true,
                                            it1,
                                            ColorProvider.listColor.first { it.numColor == userDB.graphColorLine }.color,
                                            ColorProvider.listColor.first { it.numColor == userDB.graphColorPoint }.color,
                                            userDB.graphLineShow == 1,
                                            userDB.graphTypeXLabel == 1,
                                        )
                                    }
                                } else {
                                    TextCardStandart(
                                        "All values are the same.",
                                        Modifier.fieldModifier()
                                    )

                                }
                            } else {
                                TextCardStandart(
                                    "Nothing to show. No data",
                                    Modifier.fieldModifier()
                                )

                            }
                        }

                    }
                }
            }
        }
    }
}





