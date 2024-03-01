package com.example.acelanandroid.common.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
//import co.yml.charts.axis.AxisData
//import co.yml.charts.common.model.Point
//import co.yml.charts.ui.linechart.LineChart
//import co.yml.charts.ui.linechart.model.GridLines
//import co.yml.charts.ui.linechart.model.IntersectionPoint
//import co.yml.charts.ui.linechart.model.Line
//import co.yml.charts.ui.linechart.model.LineChartData
//import co.yml.charts.ui.linechart.model.LinePlotData
//import co.yml.charts.ui.linechart.model.LineStyle
//import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
//import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
//import co.yml.charts.ui.linechart.model.ShadowUnderLine

//const val steps = 10
//
//@Composable
//fun DrawGraph(
//    x: List<Float>,
//    y: List<Float>,
//    colorBackground: Color,
//    modifier: Modifier
//) {
//    val pointList = getPointList(x, y)
//    val yScale = (y.max() - y.min()) / steps.toFloat()
//    val xAxisData = AxisData.Builder()
//        .axisStepSize(100.dp)
//        .backgroundColor(colorBackground)
//        .steps(pointList.size - 1)
//        .labelData { i -> i.toString() }
//        .labelAndAxisLinePadding(15.dp)
//        .build()
//
//    val yAxisData = AxisData.Builder()
//        .steps(steps)
//        .backgroundColor(colorBackground)
//        .labelAndAxisLinePadding(20.dp)
//        .labelData { i ->
//            String.format("%.1f",(i * yScale + y.min()))
//        }.build()
//    val lineChartData = LineChartData(
//        linePlotData = LinePlotData(
//            lines = listOf(
//                Line(
//                    dataPoints = pointList,
//                    LineStyle(),
//                    IntersectionPoint(),
//                    SelectionHighlightPoint(),
//                    ShadowUnderLine(),
//                    SelectionHighlightPopUp()
//                )
//            ),
//        ),
//        xAxisData = xAxisData,
//        yAxisData = yAxisData,
//        gridLines = GridLines(),
//        backgroundColor = Color.White
//    )
//    LineChart(
//        modifier = Modifier.fillMaxWidth()
//            .height(300.dp),
//        lineChartData = lineChartData
//    )
//
//}
//
//fun getPointList(x: List<Float>, y: List<Float>): List<Point> {
//    val list = ArrayList<Point>()
//    for (i in x.indices) {
//        list.add(
//            Point(x[i], y[i])
//        )
//    }
//    return list
//}
