package com.app.starleet.dashboardscreens

import android.app.DatePickerDialog
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.starleet.R
import com.app.starleet.intent.LactateIntent
import com.app.starleet.roomdb.LactateScanEntity
import com.app.starleet.viewmodel.LactateViewModel
import java.text.SimpleDateFormat
import java.util.*

data class ChartPoint(val label: String, val value: Float)
data class HistoryItem(val time: String, val lactate: String)
data class TrendChartData(
    val title: String,
    val subtitle: String,
    val points: ArrayList<ChartPoint>,
    val maxValue: Float
)

@Composable
fun HomeScreen(viewModel: LactateViewModel) {

    val state by viewModel.state.collectAsState()

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
            color = colorResource(id = R.color.whitecolor),
            fontFamily = FontFamily(Font(R.font.manrope_bold)),
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        CurrentLactateCard(state.current)

        Spacer(modifier = Modifier.height(16.dp))

        val trendPoints = ArrayList(
            state.trend.map {
                ChartPoint(
                    label = formatDate(it.timestamp),
                    value = it.lactateValue.toFloat()
                )
            }
        )

        val maxValue = (
                state.trend.maxOfOrNull { it.lactateValue }?.toFloat()
                    ?: 1f
                ).coerceAtLeast(1f)

        TrendChartCard(
            TrendChartData(
                title = "Trend",
                subtitle = "Last 7 scans",
                points = trendPoints,
                maxValue = maxValue
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        val filteredHistory = state.history.filter { entity ->

            state.selectedDate?.let { selected ->

                val start = getStartOfDay(selected)
                val end = start + (24 * 60 * 60 * 1000)

                entity.timestamp in start until end

            } ?: true
        }

        val historyList = filteredHistory.map {
            HistoryItem(
                time = formatTime(it.timestamp),
                lactate = "${it.lactateValue} mM"
            )
        }

        HistoryCard(
            items = historyList,
            selectedDate = state.selectedDate,
            viewModel = viewModel
        )
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Composable
fun HistoryCard(
    items: List<HistoryItem>,
    selectedDate: Long?,
    viewModel: LactateViewModel
) {

    Column {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "History",
                color = colorResource(id = R.color.whitecolor),
                fontFamily = FontFamily(Font(R.font.manrope_semibold)),
                fontWeight = FontWeight.SemiBold
            )


                DateFilter(
                    selectedDate = selectedDate,
                    onDateSelected = {
                        viewModel.processIntent(
                            LactateIntent.SelectHistoryDate(it)
                        )
                    }
                )


        }

        Spacer(modifier = Modifier.height(16.dp))

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

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Time",
                    color = colorResource(id = R.color.graymidcolor),
                    fontFamily = FontFamily(Font(R.font.manrope_medium)),
                    fontSize = 14.sp
                )
                Text(
                    text = "Lactate",
                    color = colorResource(id = R.color.graymidcolor),
                    fontFamily = FontFamily(Font(R.font.manrope_medium)),
                    fontSize = 14.sp
                )
            }

            DividerLine()

            if (items.isEmpty()) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No scans available for selected date",
                        color = White.copy(alpha = 0.6f),
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.manrope_medium))
                    )
                }

            } else {

                items.forEachIndexed { index, item ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = item.time,
                            color = White,
                            fontSize = 16.sp
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Text(
                                text = item.lactate,
                                color = White,
                                fontSize = 16.sp
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = null,
                                tint = White.copy(0.7f)
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
                    color = White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(Modifier.width(6.dp))
                Text(
                    text = "(${chartData.subtitle})",
                    color = White.copy(0.6f),
                    fontSize = 13.sp
                )
            }

            Spacer(Modifier.height(20.dp))

            if (chartData.points.isEmpty()) {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 40.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No trend data available",
                        color = White.copy(alpha = 0.6f),
                        fontSize = 14.sp
                    )
                }

            } else {

                SmoothChart(
                    points = chartData.points,
                    maxValue = chartData.maxValue
                )
            }
        }
    }
}

@Composable
private fun SmoothChart(
    points: ArrayList<ChartPoint>,
    maxValue: Float
) {

    if (points.isEmpty()) return

    val rawMax = points.maxOfOrNull { it.value } ?: 1f
    val roundedMax = kotlin.math.ceil(rawMax).coerceAtLeast(1f)
    val midValue = roundedMax / 2f

    Box(modifier = Modifier.fillMaxSize()) {

        Canvas(modifier = Modifier.fillMaxSize()) {

            val width = size.width
            val height = size.height
            val spacing =
                if (points.size > 1) width / (points.size - 1) else width

            val gridColor = White.copy(0.15f)

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

            val chartPoints = points.mapIndexed { index, point ->
                val x = spacing * index
                val y = height - (point.value / roundedMax) * height
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

        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${roundedMax.toInt()}mM",
                color = White.copy(0.6f),
                fontSize = 12.sp
            )

            Text(
                text = "${midValue.toInt()}mM",
                color = White.copy(0.6f),
                fontSize = 12.sp
            )

            Text(
                text = "0mM",
                color = White.copy(0.6f),
                fontSize = 12.sp
            )
        }

        val uniqueLabels = points.map { it.label }.distinct()

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (uniqueLabels.size == 1) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = uniqueLabels.first(),
                    color = White.copy(0.6f),
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.weight(1f))
            } else {
                uniqueLabels.forEach {
                    Text(
                        text = it,
                        color = White.copy(0.6f),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}


@Composable
fun CurrentLactateCard(current: LactateScanEntity?) {

    val cardShape = RoundedCornerShape(15.dp)

    Card(
        shape = cardShape,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp, shape = cardShape)
            .background(
                brush = Brush.linearGradient(
                    listOf(Color(0xFF1B1E21), Color(0xFF1F2F2F))
                ),
                shape = RoundedCornerShape(20.dp)
            )
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = "Current Lactate",
                color = colorResource(id = R.color.lightskyblue),
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(R.font.manrope_regular))
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {

                Text(
                    text = "${current?.lactateValue ?: 0.0} mM",
                    fontSize = 30.sp,
                    color = colorResource(id = R.color.whitecolor),
                    fontFamily = FontFamily(Font(R.font.manrope_semibold))
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = current?.sweatStatus ?: "No Sweat",
                    fontSize = 12.sp,
                    color = colorResource(id = R.color.graycolor),
                    fontFamily = FontFamily(Font(R.font.manrope_regular))
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "↓ ${current?.changeFromLast ?: 0.0} mM vs Last scan",
                fontSize = 12.sp,
                color = colorResource(id = R.color.graycolor),
                fontFamily = FontFamily(Font(R.font.manrope_regular))
            )
        }
    }
}

@Composable
private fun DividerLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(White.copy(alpha = 0.08f))
    )
}

@Composable
fun DateFilter(
    selectedDate: Long?,
    onDateSelected: (Long) -> Unit
) {

    val context = LocalContext.current

    val dateText = selectedDate?.let {
        java.text.SimpleDateFormat("dd MMM yyyy", java.util.Locale.getDefault())
            .format(java.util.Date(it))
    } ?: "Select Date"

    Row(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = White.copy(alpha = 0.2f),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {

                val cal = java.util.Calendar.getInstance()

                DatePickerDialog(
                    context,
                    { _, year, month, day ->
                        cal.set(year, month, day)
                        onDateSelected(cal.timeInMillis)
                    },
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
                ).show()

            }
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = dateText,
            color = White,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.width(4.dp))

        Icon(Icons.Default.KeyboardArrowDown, null, tint = White)
    }
}

private fun getStartOfDay(time: Long): Long {
    val cal = Calendar.getInstance()
    cal.timeInMillis = time
    cal.set(Calendar.HOUR_OF_DAY, 0)
    cal.set(Calendar.MINUTE, 0)
    cal.set(Calendar.SECOND, 0)
    cal.set(Calendar.MILLISECOND, 0)
    return cal.timeInMillis
}
fun formatTime(timestamp: Long): String {
    val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
    return sdf.format(Date(timestamp))
}

fun formatDate(timestamp: Long): String {
    val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
    return sdf.format(Date(timestamp))
}