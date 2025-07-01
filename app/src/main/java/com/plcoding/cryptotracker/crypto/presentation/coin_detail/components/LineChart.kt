package com.plcoding.cryptotracker.crypto.presentation.coin_detail.components

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.plcoding.cryptotracker.crypto.domain.models.CoinPrice
import com.plcoding.cryptotracker.crypto.presentation.models.ChartStyle
import com.plcoding.cryptotracker.crypto.presentation.models.DataPoint
import com.plcoding.cryptotracker.crypto.presentation.models.ValueLabel
import com.plcoding.cryptotracker.ui.theme.CryptoTrackerTheme
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun LineChart(
    modifier: Modifier = Modifier,
    dataPoints: List<DataPoint>,
    style: ChartStyle,
    visibleDataPointsIndices: IntRange,
    unit: String,
    selectedDataPoint: DataPoint? = null,
    onSelectedDataPointChange: (DataPoint) -> Unit = {},
    onXLabelWidthChange: (Float) -> Unit = {},
    showHelperLines: Boolean = true,
) {
    val textStyle = LocalTextStyle.current.copy(
        fontSize = style.labelFontSize
    )
    val visibleDataPoints = remember(dataPoints, visibleDataPointsIndices) {
        dataPoints.slice(visibleDataPointsIndices)


    }

    val maxYValue = remember(visibleDataPoints) {
        visibleDataPoints.maxOfOrNull { it.y } ?: 0f
    }
    val minYValue = remember(visibleDataPoints) {
        visibleDataPoints.minOfOrNull { it.y } ?: 0f
    }

    val measurer = rememberTextMeasurer()

    var xLabelWidth by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(key1 = xLabelWidth) {
        onXLabelWidthChange(xLabelWidth)
    }

    var selectedPoint by remember {
        mutableStateOf(selectedDataPoint?.let {
            dataPoints.indexOf(it)
        })
    }
    var drawPoints by remember { mutableStateOf(listOf<DataPoint>()) }

    var isShowingDataPoints by remember {
        mutableStateOf(selectedDataPoint != null)
    }


    Canvas(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(drawPoints, xLabelWidth) {
                detectHorizontalDragGestures { change, _ ->
                    val newSelectedPointIndex = getSelectedPointIndex(
                        touchOffsetX = change.position.x,
                        triggerWidth = xLabelWidth,
                        drawPoints = drawPoints
                    )
                    isShowingDataPoints =
                        (newSelectedPointIndex + visibleDataPointsIndices.first) in visibleDataPointsIndices
                    if (isShowingDataPoints) {
                        onSelectedDataPointChange(drawPoints[newSelectedPointIndex]).let {
                            selectedPoint = newSelectedPointIndex
                        }
                    }


                }

            }
            .pointerInput(drawPoints, xLabelWidth)
            {
                detectTapGestures(onTap = { offset ->
                    val newSelectedPointIndex = getSelectedPointIndex(
                        touchOffsetX = offset.x,
                        triggerWidth = xLabelWidth,
                        drawPoints = drawPoints
                    )
                    isShowingDataPoints =
                        (newSelectedPointIndex + visibleDataPointsIndices.first) in visibleDataPointsIndices
                    if (isShowingDataPoints) {
                        onSelectedDataPointChange(drawPoints[newSelectedPointIndex]).let {
                            selectedPoint = newSelectedPointIndex
                        }
                    }
                })
            }
    ) {
        val minYLabelSpacingPx = style.minYLabelSpacingDp.roundToPx()
        val verticalPaddingPx = style.verticalPadding.roundToPx()
        val horizontalPaddingPx = style.horizontalPadding.roundToPx()
        val xAxisLabelSpacingPx = style.xAxisLabelSpacing.roundToPx()

        val xLabelTextLayoutResults = visibleDataPoints.map {
            measurer.measure(
                text = it.xLabel, style = textStyle.copy(
                    textAlign = TextAlign.Center
                )
            )
        }
        val maxXLabelWidth = xLabelTextLayoutResults.maxOfOrNull { it.size.width } ?: 0f
        val maxXLabelHeight = xLabelTextLayoutResults.maxOfOrNull { it.size.height } ?: 0f
        val maxXLabelLineCount = xLabelTextLayoutResults.maxOfOrNull { it.lineCount } ?: 0
        val xLabelLineHeight =
            if (maxXLabelLineCount > 1) maxXLabelHeight.toFloat() / maxXLabelLineCount.toFloat() else 0f
        val viewPortHeightPx =
            size.height - (maxXLabelHeight.toFloat() + verticalPaddingPx * 2 + xLabelLineHeight + xAxisLabelSpacingPx)


        val labelViewPortHeightPx = viewPortHeightPx + xLabelLineHeight
        val labelCountExcludingLastOne =
            (labelViewPortHeightPx / (xLabelLineHeight + minYLabelSpacingPx)).toInt()
        val increment =
            (maxYValue - minYValue) / labelCountExcludingLastOne.toDouble()
        val yLabels = (0..labelCountExcludingLastOne).map {
            ValueLabel(
                value = maxYValue - (it * increment).toFloat(),
                unit = unit
            )
        }
        val yLabelLayoutResults = yLabels.map {
            measurer.measure(
                text = it.formatted(),
                style = textStyle
            )
        }
        val maxYLabelWidth = yLabelLayoutResults.maxOfOrNull { it.size.width } ?: 0f


        val viewPortTopY = xLabelLineHeight + verticalPaddingPx + 10f
        val viewPortRightX = size.width - horizontalPaddingPx
        val viewPortBottomY = viewPortTopY + viewPortHeightPx
        val viewPortLeftX = 2 * horizontalPaddingPx + maxYLabelWidth.toFloat()
        val viewPort = Rect(
            left = viewPortLeftX,
            top = viewPortTopY,
            right = viewPortRightX,
            bottom = viewPortBottomY
        )

        drawRect(
            color = Color.Green.copy(alpha = 0.5f),
            topLeft = viewPort.topLeft,
            size = viewPort.size
        )
        xLabelWidth = (maxXLabelWidth.toDouble() + xAxisLabelSpacingPx).toFloat()
        xLabelTextLayoutResults.forEachIndexed { index, result ->
            val xValue =
                viewPortLeftX + xAxisLabelSpacingPx / 2f + index * (xAxisLabelSpacingPx + maxXLabelWidth.toDouble())
            val yValue = viewPortBottomY + xAxisLabelSpacingPx
            drawText(
                textLayoutResult = result,

                topLeft = Offset(
                    x = xValue.toFloat(),
                    y = yValue
                ),
                color = if (index == selectedPoint) style.selectedColor else style.unselectedColor
            )

            if (showHelperLines) {
                drawLine(
                    color = if (index == selectedPoint) style.selectedColor else style.unselectedColor,
                    start = Offset(
                        x = xValue.toFloat() + result.size.width / 2f,
                        y = viewPortBottomY
                    ),
                    end = Offset(
                        x = xValue.toFloat() + result.size.width / 2f,
                        y = viewPortTopY
                    ),
                    strokeWidth = if (index == selectedPoint) style.helperLinesThicknessPx * 1.5f else style.helperLinesThicknessPx
                )
            }

            if (index == selectedPoint) {

                val valueLabel = ValueLabel(
                    value = visibleDataPoints[index].y,
                    unit = unit
                )
                val valueResult = measurer.measure(
                    text = valueLabel.formatted(),
                    style = textStyle,
                    maxLines = 1
                )
                val textPositionX = if (selectedPoint != visibleDataPointsIndices.last) {
                    xValue
                } else {
                    xValue - valueResult.size.width / 2f
                }
                val isTextInVisibleRange =
                    (size.width - textPositionX).roundToInt() in 0..size.width.roundToInt()
                if (isTextInVisibleRange) {
                    drawText(
                        textLayoutResult = valueResult,
                        topLeft = Offset(
                            x = textPositionX.toFloat(),
                            y = viewPortTopY - valueResult.size.height.toFloat() - 10f
                        ),
                        color = style.selectedColor
                    )
                }


            }
        }


        val heightRequiredForLabels = xLabelLineHeight * (labelCountExcludingLastOne + 1)
        val remainingHeightForLabels = labelViewPortHeightPx - heightRequiredForLabels
        val spaceBetweenLabels = remainingHeightForLabels / labelCountExcludingLastOne
        yLabelLayoutResults.forEachIndexed { index, result ->
            val yValue =
                viewPortTopY + index * (xLabelLineHeight + spaceBetweenLabels) - xLabelLineHeight / 2f
            drawText(
                textLayoutResult = result,
                topLeft = Offset(
                    x = horizontalPaddingPx.toFloat(),
                    y = yValue
                ),
                color = style.unselectedColor
            )
            if (showHelperLines) {
                drawLine(
                    color = style.unselectedColor,
                    start = Offset(
                        x = viewPortLeftX,
                        y = yValue + result.size.height / 2f
                    ),
                    end = Offset(
                        x = viewPortRightX,
                        y = yValue + result.size.height / 2f
                    ),
                    strokeWidth = style.helperLinesThicknessPx
                )
            }
        }

        drawPoints = visibleDataPointsIndices.map {
            val xvv =
                viewPortLeftX + (it - visibleDataPointsIndices.first + 0.5f) * xLabelWidth - 10f
            val ratio =
                (dataPoints[it].y - minYValue) / (maxYValue - minYValue)
            val y = viewPortBottomY - ratio * viewPortHeightPx
            DataPoint(
                xLabel = dataPoints[it].xLabel,
                x = xvv,
                y = y
            )
        }

        val conPoints1 = mutableListOf<DataPoint>()
        val conPoints2 = mutableListOf<DataPoint>()
        for (i in 1 until drawPoints.size) {
            val p0 = drawPoints[i - 1]
            val p1 = drawPoints[i]
            val x = (p1.x + p0.x) / 2f
            val y1 = p0.y
            val y2 = p1.y
            conPoints1.add(DataPoint(xLabel = "", x = x, y = y1))
            conPoints2.add(DataPoint(xLabel = "", x = x, y = y2))
        }
        val linePath = Path().apply {
            if (drawPoints.isNotEmpty()) {
                moveTo(x = drawPoints.first().x, y = drawPoints.first().y)
            }


            for (i in 1 until drawPoints.size) {
                cubicTo(
                    x1 = conPoints1[i - 1].x,
                    y1 = conPoints1[i - 1].y,
                    x2 = conPoints2[i - 1].x,
                    y2 = conPoints2[i - 1].y,
                    x3 = drawPoints[i].x,
                    y3 = drawPoints[i].y
                )
            }
        }

        drawPath(
            path = linePath,
            color = style.chartLineColor,
            style = Stroke(
                width = 5f,
                cap = StrokeCap.Round
            )
        )




        drawPoints.forEachIndexed { index, dataPoint ->
            if (isShowingDataPoints) {
                val circleOffset = Offset(
                    x = dataPoint.x,
                    y = dataPoint.y
                )

                drawCircle(
                    color = style.selectedColor,
                    center = circleOffset,
                    radius = 10f
                )
                if (selectedPoint == index) {
                    drawCircle(
                        color = Color.White,
                        center = circleOffset,
                        radius = 15f
                    )
                    drawCircle(
                        color = style.selectedColor,
                        center = circleOffset,
                        radius = 15f,
                        style = Stroke(width = 3f)
                    )
                }
            }

        }

    }
}


private fun getSelectedPointIndex(
    touchOffsetX: Float,
    triggerWidth: Float,
    drawPoints: List<DataPoint>
): Int {
    val triggerRangeLeft = touchOffsetX - triggerWidth / 2f
    val triggerRangerRight = touchOffsetX + triggerWidth / 2f
    return drawPoints.indexOfFirst {
        it.x in triggerRangeLeft..triggerRangerRight
    }

}

@Preview
@Composable
private fun LineChartPreview() {
    CryptoTrackerTheme {
        val coinHistoryRandomData = remember {
            (1..20).map {
                CoinPrice(
                    priceUsd = Random.nextDouble() * 1000,
                    dateTime = Date(System.currentTimeMillis()).apply { time -= it * 60 * 60 * 1000 }
                )
            }
        }
        val style = ChartStyle(
            chartLineColor = Color.Black,
            unselectedColor = Color(0xFF7C7C7C),
            selectedColor = Color.Black,
            helperLinesThicknessPx = 1f,
            axisLinesThicknessPx = 5f,
            labelFontSize = 14.sp,
            minYLabelSpacingDp = 25.dp,
            verticalPadding = 8.dp,
            horizontalPadding = 8.dp,
            xAxisLabelSpacing = 8.dp
        )
        val dataPoints = remember {
            coinHistoryRandomData.map { coinPrice ->
                DataPoint(
                    xLabel = SimpleDateFormat("ha\nd/M").format(coinPrice.dateTime),
                    y = coinPrice.priceUsd.toFloat(),
                    x = coinPrice.dateTime.hours.toFloat()
                )
            }
        }

        LineChart(
            modifier = Modifier
                .width(1000.dp)
                .height(300.dp)
                .background(Color.White),
            dataPoints = dataPoints,
            style = style,
            visibleDataPointsIndices = 0..19,
            unit = "$",
            selectedDataPoint = dataPoints[1],
        )
    }

}