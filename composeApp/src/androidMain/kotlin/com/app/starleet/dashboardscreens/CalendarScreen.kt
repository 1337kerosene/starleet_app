package com.app.starleet.dashboardscreens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.starleet.R
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen() {

    var currentMonth by remember { mutableStateOf(YearMonth.now()) }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    val firstDayOfMonth = currentMonth.atDay(1)
    val daysInMonth = currentMonth.lengthOfMonth()

    // Adjust so week starts from Sunday
    val startOffset = (firstDayOfMonth.dayOfWeek.value % 7)

    val daysList = mutableListOf<LocalDate?>()

    // Add empty spaces before first day
    repeat(startOffset) {
        daysList.add(null)
    }

    // Add actual days
    for (day in 1..daysInMonth) {
        daysList.add(currentMonth.atDay(day))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000)) // Black background
            .systemBarsPadding()
            .padding(16.dp)
    ) {

        // Calendar Header
        Text(
            text = "Scan History",
            color = colorResource(id = R.color.whitecolor),
            fontFamily = FontFamily(Font(R.font.manrope_bold)),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Calendar Box
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF1F2A2E),
                            Color(0xFF132325)
                        )
                    )
                )
                .padding(16.dp)
        ) {

            Column {

                // Header (Month + Arrows)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    IconButton(onClick = {
                        currentMonth = currentMonth.minusMonths(1)
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Previous",
                            tint = Color.White
                        )
                    }

                    Text(
                        text = "${
                            currentMonth.month.getDisplayName(
                                TextStyle.SHORT,
                                Locale.getDefault()
                            )
                        } ${currentMonth.year}",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )

                    IconButton(onClick = {
                        currentMonth = currentMonth.plusMonths(1)
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Next",
                            tint = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Days of week header
                val daysOfWeek = DayOfWeek.values()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    daysOfWeek.forEach { day ->
                        Text(
                            text = day.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                            color = Color.LightGray,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Calendar Grid
                daysList.chunked(7).forEach { week ->

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        week.forEach { date ->

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(4.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (date != null && date == selectedDate)
                                            Color(0xFF1EC8A5)
                                        else
                                            Color.Transparent
                                    )
                                    .clickable(enabled = date != null) {
                                        if (date != null) selectedDate = date
                                    },
                                contentAlignment = Alignment.Center
                            ) {

                                Text(
                                    text = date?.dayOfMonth?.toString() ?: "",
                                    color = when {
                                        date == null -> Color.Transparent
                                        date == selectedDate -> Color.Black
                                        else -> Color.White
                                    },
                                    fontSize = 14.sp,
                                    fontWeight = if (date == selectedDate)
                                        FontWeight.Bold
                                    else
                                        FontWeight.Normal
                                )
                            }
                        }

                        // Fill remaining cells in last row
                        if (week.size < 7) {
                            repeat(7 - week.size) {
                                Spacer(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .padding(4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // Spacer between calendar and scan history
        Spacer(modifier = Modifier.height(16.dp))

        // Bottom Section: Scan History
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))

                .padding(4.dp)
        ) {
            Column {
                // Scan History Header
                Text(
                    text = "Monday 08, Dec",
                    color = colorResource(id = R.color.graycolor),
                    fontFamily = FontFamily(Font(R.font.manrope_semibold)),                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = "3 Scans Recorded",
                    color = colorResource(id = R.color.graylightcolorAFAFAF),
                    fontFamily = FontFamily(Font(R.font.manrope_regular)),                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // LazyColumn for scan items
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        listOf(
                            ScanData("07:32 AM", "Lactose: 2.0 mM", Color(0xFF2F8D91)),
                            ScanData("07:32 AM", "Lactose: 2.0 mM", Color(0xFFE03675)),
                            ScanData("07:37 AM", "Lactose: 2.0 mM", Color(0xFF87E64C))
                        )
                    ) { scan ->
                        ScanItem(scan = scan)
                    }
                }
            }
        }
    }
}

data class ScanData(val time: String, val value: String, val color: Color)

@Composable
fun ScanItem(scan: ScanData) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp) // Vertical spacing between cards
            .clip(RoundedCornerShape(8.dp)) // Rounded corners to match the card
            .background(Color(0xFF111111)) // Dark background color for the card
            .padding(horizontal = 8.dp, vertical = 4.dp) // Inner padding for the card
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp) // Width of the vertical line
                    .height(40.dp) // Height of the vertical line
                    .background(scan.color) // Colored vertical line based on scan color
            )
            Spacer(modifier = Modifier.width(12.dp)) // Space between the line and the time text

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min) // Ensure the column takes minimum space
            ) {
                // Left side: Time with the colored pill
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = scan.time,
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                }

                // Spacer to push the scan value to the bottom of the card

                // Right side: Scan Value
                Row(
                    horizontalArrangement = Arrangement.Start, // Align scan value to the right
                    modifier = Modifier.fillMaxWidth() // Ensure it takes full width
                ) {
                    Text(
                        text = scan.value,
                        color = scan.color,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalendarPreview() {
    MaterialTheme {
        CalendarScreen()
    }
}