package com.app.starleet.dashboardscreens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import com.app.starleet.R
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle

data class ChartPoint(
    val label: String,
    val value: Float
)

data class HistoryItem(
    val time: String,
    val lactate: String
)

data class TrendChartData(
    val title: String,
    val subtitle: String,
    val points: ArrayList<ChartPoint>,
    val maxValue: Float
)

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
            .padding(14.dp)
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Overview",
            color = White,
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp)) // Space between items

        CurrentLactateCard()

        Spacer(modifier = Modifier.height(16.dp)) // Space between items


        val staticChartData = TrendChartData(
            title = "Trend",
            subtitle = "Last 7 scans",
            points = arrayListOf(
                ChartPoint("9 Jan", 0.6f),
                ChartPoint("11 Jan", 1.8f),
                ChartPoint("13 Jan", 1.5f),
                ChartPoint("15 Jan", 1.0f),
                ChartPoint("17 Jan", 2.9f),
                ChartPoint("20 Jan", 3.0f),
                ChartPoint("22 Jan", 3.8f)
            ),
            maxValue = 4f
        )
        TrendChartCard(staticChartData, modifier = Modifier)

        Spacer(modifier = Modifier.height(16.dp))

        val historyList = arrayListOf(
            HistoryItem("07:03", "0.0 mM"),
            HistoryItem("07:32", "2.8 mM"),
            HistoryItem("07:40", "2.9 mM"),
            HistoryItem("07:48", "2.5 mM")
        )


        HistoryCard(historyList)

        Spacer(modifier = Modifier.height(80.dp))
    }
}


@Composable
fun TrendChartCard(
    chartData: TrendChartData,
    modifier: Modifier = Modifier
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(260.dp)
            .background(
                brush = Brush.linearGradient(
                    listOf(Color(0xFF1B1E21), Color(0xFF1F2F2F))
                ),
                shape = RoundedCornerShape(20.dp)
            )
            .padding(16.dp)
    ) {

        Column {

            Row {
                Text(
                    text = chartData.title,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = "(${chartData.subtitle})",
                    color = Color.White.copy(0.6f),
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.height(20.dp))

            SmoothChart(
                points = chartData.points,
                maxValue = chartData.maxValue
            )
        }
    }
}

@Composable
private fun SmoothChart(
    points: ArrayList<ChartPoint>,
    maxValue: Float
) {

    Box(modifier = Modifier.fillMaxSize()) {

        Canvas(modifier = Modifier.fillMaxSize()) {

            val width = size.width
            val height = size.height

            val spacing = width / (points.size - 1)

            // GRID
            val gridColor = Color.White.copy(0.15f)

            for (i in 0..2) {
                val y = height - (height / 2f * i)
                drawLine(
                    color = gridColor,
                    start = Offset(0f, y),
                    end = Offset(width, y),
                    strokeWidth = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f))
                )
            }

            for (i in points.indices) {
                val x = spacing * i
                drawLine(
                    color = gridColor,
                    start = Offset(x, 0f),
                    end = Offset(x, height),
                    strokeWidth = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(8f, 8f))
                )
            }

            // CREATE SMOOTH PATH
            val chartPoints = points.mapIndexed { index, point ->
                val x = spacing * index
                val y = height - (point.value / maxValue) * height
                Offset(x, y)
            }

            val path = Path()
            val fillPath = Path()

            path.moveTo(chartPoints.first().x, chartPoints.first().y)
            fillPath.moveTo(chartPoints.first().x, height)
            fillPath.lineTo(chartPoints.first().x, chartPoints.first().y)

            for (i in 0 until chartPoints.size - 1) {
                val p1 = chartPoints[i]
                val p2 = chartPoints[i + 1]

                val controlX = (p1.x + p2.x) / 2

                path.cubicTo(controlX, p1.y, controlX, p2.y, p2.x, p2.y)
                fillPath.cubicTo(controlX, p1.y, controlX, p2.y, p2.x, p2.y)
            }

            fillPath.lineTo(width, height)
            fillPath.close()

            drawPath(
                path = fillPath,
                brush = Brush.verticalGradient(
                    listOf(Color(0xFF2ED3C6), Color(0x332ED3C6))
                )
            )

            drawPath(
                path = path,
                color = Color(0xFF2ED3C6),
                style = Stroke(width = 4f, cap = StrokeCap.Round)
            )
        }

        // Y Axis dynamic
        Column(
            modifier = Modifier.align(Alignment.CenterStart),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            val steps = 3
            val stepValue = maxValue / (steps - 1)
            for (i in steps - 1 downTo 0) {
                Text(
                    text = "${(stepValue * i).toInt()}m",
                    color = Color.White.copy(0.6f),
                    fontSize = 12.sp
                )
            }
        }

        // X Axis dynamic from ArrayList
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            points.forEach {
                Text(
                    text = it.label,
                    color = Color.White.copy(0.6f),
                    fontSize = 12.sp
                )
            }
        }
    }
}

@Composable
fun CurrentLactateCard() {
    Card(
        shape = RoundedCornerShape(15.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF333333)), // Dark gray background
        modifier = Modifier
            .fillMaxWidth()

            .shadow(4.dp, shape = RoundedCornerShape(15.dp)) // Added shadow for elevation
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(15.dp))
                .background(
                    Brush.horizontalGradient(
                        colorStops = arrayOf(
                            0.0f to Color(0xFF24A19D),                 // Teal
                            0.5f to Color(0xFF454545),                 // Dark Gray
                            1.0f to Color(0xFF000000).copy(alpha = 0.20f) // 20% black
                        )
                    )
                )
        )


        // 👉 Your content goes here
        Column(modifier = Modifier.padding(16.dp)) {
            // Current Lactate Header
            Text(
                text = "Current Lactate",
                color = Color(0xFF00E5B0), // Teal color for the header
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Lactate Value and Status on a Single Line
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Lactate value
                Text(
                    text = "0.1 mM",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )

                Spacer(modifier = Modifier.width(8.dp)) // Add space between lactate value and status

                // Status (No Sweat)
                Text(
                    text = "No Sweat",
                    fontSize = 12.sp,
                    color = Gray
                )
            }

            // Difference from Last scan
            Text(
                text = "↓ 0.3 mM vs Last scan",
                fontSize = 12.sp,
                color = Gray
            )
        }
    }
}


@Composable
fun HistoryCard(
    items: List<HistoryItem>
) {

    Column(

    ) {

        // Header Row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "History",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            // Today Dropdown Button
            Row(
                modifier = Modifier
                    .border(
                        width = 1.dp,
                        color = Color.White.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Today",
                    color = Color.White,
                    fontSize = 14.sp
                )
                Spacer(modifier = Modifier.width(4.dp))
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card Container
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            Color(0xFF1B1E21),
                            Color(0xFF1F2F2F)
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(vertical = 12.dp)
        ) {

            // Table Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Time",
                    color = Color.White.copy(0.6f),
                    fontSize = 14.sp
                )
                Text(
                    text = "Lactate",
                    color = Color.White.copy(0.6f),
                    fontSize = 14.sp
                )
            }

            DividerLine()

            items.forEachIndexed { index, item ->

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = item.time,
                        color = Color.White,
                        fontSize = 16.sp
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Row(verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = item.lactate,
                            color = Color.White,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = null,
                            tint = Color.White.copy(0.7f)
                        )
                    }
                }

                if (index != items.lastIndex) {
                    DividerLine()
                }
            }
        }
    }
}

@Composable
private fun DividerLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.White.copy(alpha = 0.08f))
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DashboardPreview() {
    MaterialTheme {
        HomeScreen()
    }
}