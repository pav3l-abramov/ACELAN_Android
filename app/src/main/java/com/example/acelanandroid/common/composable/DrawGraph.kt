package com.example.acelanandroid.common.composable

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
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

@Composable
fun PointChart(xValues: List<Float>, yValues: List<Float>,modifier: Modifier) {
    val maxValueX = xValues.maxOrNull() ?: 1f
    val maxValueY = yValues.maxOrNull() ?: 1f
    val configuration = LocalConfiguration.current
    val screenMin = listOf(configuration.screenHeightDp.dp,configuration.screenWidthDp.dp).min()

    Column (modifier.background(Color.White)) {
    Canvas(modifier = modifier.height(screenMin)
        .width(screenMin),

        ) {
        val padding = 32f
        val width = size.width - padding * 2
        val height = size.height - padding * 2

        val xRange = xValues.maxOrNull()?.minus(xValues.minOrNull() ?: 0f) ?: 1f
        val yRange = yValues.maxOrNull()?.minus(yValues.minOrNull() ?: 0f) ?: 1f

        // Draw x-axis
        drawLine(
            start = Offset(padding, size.height - padding),
            end = Offset(size.width - padding, size.height - padding),
            color = Color.Black
        )

        // Draw y-axis
        drawLine(
            start = Offset(padding, size.height - padding),
            end = Offset(padding, padding),
            color = Color.Black
        )

        // Draw points
        xValues.zip(yValues).forEach { (x, y) ->
            val pointX = padding + (width * (x - xValues.minOrNull()!!) / xRange)
            val pointY = size.height - padding - (height * (y - yValues.minOrNull()!!) / yRange)

            drawCircle(
                color = Color.Blue,
                center = Offset(pointX, pointY),
                radius = 20f
            )
        }
    }
}
}
@Composable
@Preview
fun qweqw(){
    PointChart(listOf(1.0f,2.0f,10.0f),  listOf(1.0f,2.0f,5.0f), Modifier)
}