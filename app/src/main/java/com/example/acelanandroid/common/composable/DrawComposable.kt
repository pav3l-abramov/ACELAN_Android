package com.example.acelanandroid.common.composable

import android.graphics.Paint
import android.graphics.PointF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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

data class GraphAppearance(
    val graphColor: Color,
    val graphAxisColor: Color,
    val graphThickness: Float,
    val iscolorAreaUnderChart: Boolean,
    val colorAreaUnderChart: Color,
    val isCircleVisible: Boolean,
    val circleColor: Color,
    val backgroundColor: Color
)

const val stepsX = 4
const val stepsY = 6
//
//@Composable
//fun PointChart2(xValues: List<Float>, yValues: List<Float>, modifier: Modifier) {
//    val yMin = yValues.min()
//    val yMax = yValues.max()
//    val xMin = xValues.min()
//    val xMax = xValues.max()
//    val yStep = (yMax - yMin) / steps.toFloat()
//    val xStep = (xMax - xMin) / steps.toFloat()
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.DarkGray)
//    ) {
//        Graph(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(500.dp),
//            xValuesLabel = (0..steps).map { (it) * xStep + xMin },
//            yValuesLabel = (0..steps).map { (it) * yStep + yMin },
//            yValues = yValues,
//            xValues = xValues,
//            paddingSpace = 16.dp,
//            verticalStep = yStep,
//            graphAppearance = GraphAppearance(
//                Color.Gray,
//                MaterialTheme.colorScheme.primary,
//                1f,
//                false,
//                Color.Green,
//                true,
//                MaterialTheme.colorScheme.secondary,
//                MaterialTheme.colorScheme.background
//            )
//        )
//    }
//}
//
//@Composable
//fun Graph(
//    modifier: Modifier,
//    xValuesLabel: List<Float>,
//    yValuesLabel: List<Float>,
//    yValues: List<Float>,
//    xValues: List<Float>,
//    paddingSpace: Dp,
//    verticalStep: Float,
//    graphAppearance: GraphAppearance
//) {
//    val controlPoints1 = mutableListOf<PointF>()
//    val controlPoints2 = mutableListOf<PointF>()
//    val coordinates = mutableListOf<PointF>()
//    val density = LocalDensity.current
//    val textPaint = remember(density) {
//        Paint().apply {
//            color = graphAppearance.graphAxisColor.toArgb()
//            textAlign = Paint.Align.CENTER
//            textSize = density.run { 12.sp.toPx() }
//        }
//    }
//    val configuration = LocalConfiguration.current
//    val screenMin = listOf(configuration.screenHeightDp.dp, configuration.screenWidthDp.dp).min()
//
//
//    Box(
//        modifier = modifier
//            .background(graphAppearance.backgroundColor)
//            .padding(horizontal = 8.dp, vertical = 12.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        Canvas(
//            modifier = Modifier
//                .width(screenMin)
//                .height(screenMin),
//        ) {
//            val xAxisSpace = (size.width - paddingSpace.toPx()) / (xValuesLabel.size + 1)
//            val yAxisSpace = size.height / yValuesLabel.size
//            /** placing x axis points */
//            /** placing x axis points */
//            for (i in xValuesLabel.indices) {
//                drawContext.canvas.nativeCanvas.drawText(
//                    "${xValuesLabel[i]}",
//                    xAxisSpace * (i + 1),
//                    size.height - 30,
//                    textPaint
//                )
//            }
//            /** placing y axis points */
//            /** placing y axis points */
//            for (i in yValuesLabel.indices) {
//                drawContext.canvas.nativeCanvas.drawText(
//                    "${yValuesLabel[i]}",
//                    paddingSpace.toPx() / 2f,
//                    size.height - yAxisSpace * (i + 1),
//                    textPaint
//                )
//            }
//            /** placing our x axis points */
//            /** placing our x axis points */
//            for (i in yValues.indices) {
//                val x1 = xAxisSpace * xValues[i]
//                val y1 =
//                    size.height - (yAxisSpace * (yValues[i]) / verticalStep) - paddingSpace.toPx()
//                coordinates.add(PointF(x1, y1))
//                /** drawing circles to indicate all the points */
//                /** drawing circles to indicate all the points */
//                if (graphAppearance.isCircleVisible) {
//                    drawCircle(
//                        color = graphAppearance.circleColor,
//                        radius = 10f,
//                        center = Offset(x1, y1)
//                    )
//                }
//            }
//            /** calculating the connection points */
//            /** calculating the connection points */
//            for (i in 1 until coordinates.size) {
//                controlPoints1.add(
//                    PointF(
//                        (coordinates[i].x + coordinates[i - 1].x) / 2,
//                        coordinates[i - 1].y
//                    )
//                )
//                controlPoints2.add(
//                    PointF(
//                        (coordinates[i].x + coordinates[i - 1].x) / 2,
//                        coordinates[i].y
//                    )
//                )
//            }
//            /** drawing the path */
//            /** drawing the path */
//            val stroke = Path().apply {
//                reset()
//                moveTo(coordinates.first().x, coordinates.first().y)
//                for (i in 0 until coordinates.size - 1) {
//                    cubicTo(
//                        controlPoints1[i].x, controlPoints1[i].y,
//                        controlPoints2[i].x, controlPoints2[i].y,
//                        coordinates[i + 1].x, coordinates[i + 1].y
//                    )
//                }
//            }
//            /** filling the area under the path */
//            /** filling the area under the path */
//            val fillPath = android.graphics.Path(stroke.asAndroidPath())
//                .asComposePath()
//                .apply {
//                    lineTo(xAxisSpace * xValuesLabel.last(), size.height - yAxisSpace)
//                    lineTo(xAxisSpace, size.height - yAxisSpace)
//                    close()
//                }
//            if (graphAppearance.iscolorAreaUnderChart) {
//                drawPath(
//                    fillPath,
//                    brush = Brush.verticalGradient(
//                        listOf(
//                            graphAppearance.colorAreaUnderChart,
//                            Color.Transparent,
//                        ),
//                        endY = size.height - yAxisSpace
//                    ),
//                )
//            }
//            drawPath(
//                stroke,
//                color = graphAppearance.graphColor,
//                style = Stroke(
//                    width = graphAppearance.graphThickness,
//                    cap = StrokeCap.Round
//                )
//            )
//        }
//    }
//}

@Composable
@Preview
fun qweqw() {
    PointChart(
        120.dp,
        listOf(1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f),
        listOf(0.0f, 2.0f, 3.0f, 10.0f, 5.0f, 4.0f),
        "x,n",
        "y,n",
        true,
        listOf("pzt1", "pzt2", "pzt3", "pzt4", "pzt5")
    )
}

@Composable
fun PointChart(
    paddingSpace: Dp,
    xValues: List<Float>,
    yValues: List<Float>,
    xLabelName: String,
    yLabelName: String,
    isMaterial: Boolean,
    materialNameList: List<String>,
) {
    val maxValueX = xValues.maxOrNull() ?: 1f
    val maxValueY = yValues.maxOrNull() ?: 1f
    val minValueX = xValues.minOrNull() ?: 1f
    val minValueY = yValues.minOrNull() ?: 1f
    val yStep = (maxValueY - minValueY) / stepsY.toFloat()
    val xStep = (maxValueX - minValueX) / stepsX.toFloat()
    val xValuesLabel = (0..stepsX).map { (it) * xStep + minValueX }
    val yValuesLabel = (0..stepsY).map { (it) * yStep + minValueY }
    val xValuesLabelMaterial = (materialNameList.indices).map { (it) }
    val configuration = LocalConfiguration.current
    val screenMin = listOf(configuration.screenHeightDp.dp, configuration.screenWidthDp.dp).min()
    val density = LocalDensity.current
    val coordinates = mutableListOf<PointF>()
    val bezierPath = remember { Path() }

    val textPaint = remember(density) {
        Paint().apply {
            color = Color.Black.toArgb()
            textAlign = Paint.Align.CENTER
            textSize = density.run { 14.sp.toPx() }
        }
    }
    val textPaintLabel = remember(density) {
        Paint().apply {
            color = Color.Black.toArgb()
            textAlign = Paint.Align.CENTER
            textSize = density.run { 18.sp.toPx() }
        }
    }

    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Canvas(
            modifier = Modifier
                .height(screenMin)
                .width(screenMin)
                .background(Color.White)
                .align(Alignment.CenterHorizontally),

            ) {
            val padding = 72f
            val xRange = maxValueX.minus(minValueX)
            val yRange = maxValueY.minus(minValueY)

            // Draw x-axis
            drawLine(
                start = Offset(
                    padding / 2 + paddingSpace.toPx() / 2,
                    size.height - padding / 2 - paddingSpace.toPx() / 2
                ),
                end = Offset(
                    size.width - padding / 2,
                    size.height - padding / 2 - paddingSpace.toPx() / 2
                ),
                color = Color.Black
            )

            // Draw y-axis
            drawLine(
                start = Offset(
                    padding / 2 + paddingSpace.toPx() / 2,
                    size.height - padding / 2 - paddingSpace.toPx() / 2
                ),
                end = Offset(padding / 2 + paddingSpace.toPx() / 2, padding / 2),
                color = Color.Black
            )
            drawContext.canvas.nativeCanvas.drawText(
                String.format(xLabelName),
                (size.width - padding * 2 - paddingSpace.toPx() / 2) / 2 + padding + paddingSpace.toPx() / 2,
                size.height - padding,
                textPaintLabel
            )
            val rect = Rect(Offset.Zero, size)
            rotate(degrees = -90f, rect.center) {
                drawContext.canvas.nativeCanvas.drawText(
                    String.format(yLabelName),
                    (size.width - padding * 2 - paddingSpace.toPx() / 2) / 2 + padding + paddingSpace.toPx() / 2,
                    padding,
                    textPaintLabel
                )
            }
            val sizeMaterial = materialNameList.size - 1
            if (isMaterial) {

                for (i in xValuesLabelMaterial.indices) {
                    drawContext.canvas.nativeCanvas.drawText(
                        String.format(materialNameList[i]),
                        i * (size.width - padding * 2 - paddingSpace.toPx() / 2) / sizeMaterial + padding + paddingSpace.toPx() / 2,
                        size.height + padding / 2 - paddingSpace.toPx() / 2,
                        textPaint
                    )

                    drawLine(
                        start = Offset(
                            i * (size.width - padding * 2 - paddingSpace.toPx() / 2) / sizeMaterial + padding + paddingSpace.toPx() / 2,
                            size.height - padding / 2 - paddingSpace.toPx() / 2
                        ),
                        end = Offset(
                            i * (size.width - padding * 2 - paddingSpace.toPx() / 2) / sizeMaterial + padding + paddingSpace.toPx() / 2,
                            padding / 2
                        ),
                        color = Color.Gray
                    )
                }
            } else {
                for (i in xValuesLabel.indices) {
                    drawContext.canvas.nativeCanvas.drawText(
                        String.format("%.1f", (xValuesLabel[i])),
                        i * (size.width - padding * 2 - paddingSpace.toPx() / 2) / stepsX + padding + paddingSpace.toPx() / 2,
                        size.height + padding / 2 - paddingSpace.toPx() / 2,
                        textPaint
                    )

                    drawLine(
                        start = Offset(
                            i * (size.width - padding * 2 - paddingSpace.toPx() / 2) / stepsX + padding + paddingSpace.toPx() / 2,
                            size.height - padding / 2 - paddingSpace.toPx() / 2
                        ),
                        end = Offset(
                            i * (size.width - padding * 2 - paddingSpace.toPx() / 2) / stepsX + padding + paddingSpace.toPx() / 2,
                            padding / 2
                        ),
                        color = Color.Gray
                    )
                }

            }
            for (i in yValuesLabel.indices) {
                drawContext.canvas.nativeCanvas.drawText(
                    String.format("%.1f", (yValuesLabel[i])),
                    padding / 2 + paddingSpace.toPx() / 2 - 12 * String.format(
                        "%.1f",
                        (yValuesLabel[i])
                    ).length,
                    (size.height - padding * 2 - paddingSpace.toPx() / 2) * (1 * stepsY - i) / stepsY + padding,
                    textPaint
                )
                drawLine(
                    start = Offset(
                        padding / 2 + paddingSpace.toPx() / 2,
                        (size.height - padding * 2 - paddingSpace.toPx() / 2) * (1 * stepsY - i) / stepsY + padding
                    ),
                    end = Offset(
                        size.width - padding / 2,
                        (size.height - padding * 2 - paddingSpace.toPx() / 2) * (1 * stepsY - i) / stepsY + padding
                    ),
                    color = Color.Gray
                )
            }
if (isMaterial) {
    // Draw points
    xValuesLabelMaterial.zip(yValues).forEach { (x, y) ->
        val pointX =
            padding + paddingSpace.toPx() / 2 + ((size.width - padding * 2 - paddingSpace.toPx() / 2) * (x) / sizeMaterial)
        val pointY =
            size.height - padding - paddingSpace.toPx() / 2 - ((size.height - padding * 2 - paddingSpace.toPx() / 2) * (y - minValueY) / yRange)
        // i * (size.width-padding*2  -paddingSpace.toPx() / 2) / stepsX + padding + paddingSpace.toPx() / 2
        coordinates.add(PointF(pointX, pointY))
    }

}
            else{
    xValues.zip(yValues).forEach { (x, y) ->
        val pointX =
            padding + paddingSpace.toPx() / 2 + ((size.width - padding * 2 - paddingSpace.toPx() / 2) * (x - minValueX) / xRange)
        val pointY =
            size.height - padding - paddingSpace.toPx() / 2 - ((size.height - padding * 2 - paddingSpace.toPx() / 2) * (y - minValueY) / yRange)
        // i * (size.width-padding*2  -paddingSpace.toPx() / 2) / stepsX + padding + paddingSpace.toPx() / 2
        coordinates.add(PointF(pointX, pointY))
    }
            }
            if (bezierPath.isEmpty) {
                for (i in coordinates.indices) {
                    if (i == 0) {
                        bezierPath.moveTo(coordinates[i].x, coordinates[i].y)
                    } else {
                        bezierPath.cubicTo(
                            coordinates[i - 1].x,
                            coordinates[i - 1].y,
                            coordinates[i - 1].x + 30f,
                            coordinates[i - 1].y + 30f, // Control points for Bezier curve
                            coordinates[i].x,
                            coordinates[i].y
                        )
                    }


                }
            }
            coordinates.forEach { coord ->
                drawCircle(
                    color = Color.Black,
                    center = Offset(coord.x, coord.y),
                    radius = 20f
                )
            }


            drawPath(
                path = bezierPath,
                color = Color.Red,
                style = Stroke(width = 5f)
            )
        }
    }
}