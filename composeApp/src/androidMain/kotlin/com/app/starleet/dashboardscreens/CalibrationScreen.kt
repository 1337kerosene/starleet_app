package com.app.starleet.dashboardscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.starleet.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalibrationScreen(
    onBackClick: () -> Unit = {},
    onDiscardClick: () -> Unit = {},
    onSaveClick: (Float) -> Unit = {}
) {

    var calibrationValue by remember { mutableStateOf(50f) }

    Scaffold(
        containerColor = Color(0xFF0E0E0E),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Calibration",
                        color = colorResource(id = R.color.whitecolor),
                        fontFamily = FontFamily(Font(R.font.manrope_bold)),
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0E0E0E)
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0E0E0E))
                .padding(innerPadding)
                .padding(16.dp)
                .navigationBarsPadding(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            // Top Content
            CalibrationCard(
                sliderValue = calibrationValue,
                onValueChange = { calibrationValue = it },
                onResetClick = { calibrationValue = 50f }
            )

            // Bottom Buttons (Fixed at bottom)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedButton(
                    onClick = onDiscardClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Discard")
                }

                Button(
                    onClick = { onSaveClick(calibrationValue) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1FA39A)
                    )
                ) {
                    Text("Save")
                }
            }
        }
    }
}

@Composable
fun CalibrationCard(
    sliderValue: Float,
    onValueChange: (Float) -> Unit,
    onResetClick: () -> Unit
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {

        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF1C2B2B),
                            Color(0xFF213737)
                        )
                    ),
                    shape = RoundedCornerShape(18.dp)
                )
                .padding(horizontal = 18.dp, vertical = 18.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Spacer(modifier = Modifier.weight(1f))

                    Text(
                        text = "Calibration",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.manrope_bold))
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    IconButton(
                        onClick = onResetClick,
                        modifier = Modifier.size(20.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            tint = Color(0xFFB5B5B5),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "${sliderValue.toInt()}%",
                    color = Color(0xFF4FD1C5),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(14.dp))

                Slider(
                    value = sliderValue,
                    onValueChange = onValueChange,
                    valueRange = 0f..100f,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp),
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFFE0E0E0),
                        activeTrackColor = Color(0xFF4FD1C5),
                        inactiveTrackColor = Color(0xFF555555)
                    )
                )

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("0%", color = Color(0xFFB0B0B0), fontSize = 11.sp)
                    Text("100%", color = Color(0xFFB0B0B0), fontSize = 11.sp)
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Changing this value may affect measurement accuracy.",
                    color = Color(0xFF9A9A9A),
                    fontSize = 11.sp
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CalibrationPreview() {
    MaterialTheme {
        Surface {
            CalibrationScreen()
        }
    }
}