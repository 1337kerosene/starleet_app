package com.app.starleet.dashboardscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.starleet.R
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaisScreen(
    onBackClick: () -> Unit = {},
    onDiscardClick: () -> Unit = {},
    onSaveClick: (Float) -> Unit = {}
) {
    var biasValue by remember { mutableStateOf(0f) }

    Scaffold(
        containerColor = Color(0xFF0E0E0E),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Bias",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_back_arrow),
                            contentDescription = "Back",
                            tint = Color.Unspecified,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF0E0E0E)
                )
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0E0E0E))
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            // Top Content
            BiasCard(
                biasValue = biasValue,
                onBiasChange = { biasValue = it },
                onResetClick = { biasValue = 0f }
            )

            // Bottom Buttons (Now fixed at bottom)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                OutlinedButton(
                    onClick = onDiscardClick,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.White
                    )
                ) {
                    Text("Discard")
                }

                Button(
                    onClick = { onSaveClick(biasValue) },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1FA39A),
                        contentColor = Color.White
                    )
                ) {
                    Text("Save")
                }
            }
        }
    }
}

@Composable
fun BiasCard(
    biasValue: Float,
    onBiasChange: (Float) -> Unit,
    onResetClick: () -> Unit
) {
    val minValue = -150f
    val maxValue = 150f

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {

        Box(
            modifier = Modifier
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF1B2A2A),
                            Color(0xFF1E3A3A)
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(horizontal = 20.dp, vertical = 22.dp)
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // Top Row (Title centered + refresh right)
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {

                    Text(
                        text = "Bias (mV)",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.align(Alignment.Center)
                    )

                    IconButton(
                        onClick = onResetClick,
                        modifier = Modifier
                            .align(Alignment.CenterEnd)
                            .size(22.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            tint = Color.LightGray,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Value
                Text(
                    text = "${biasValue.toInt()} mV",
                    color = Color(0xFF4FD1C5),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(18.dp))

                // Slider
                Slider(
                    value = biasValue,
                    onValueChange = onBiasChange,
                    valueRange = minValue..maxValue,
                    modifier = Modifier.fillMaxWidth(),
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFFDADADA),
                        activeTrackColor = Color(0xFF4FD1C5),
                        inactiveTrackColor = Color(0xFF5A5A5A),
                        activeTickColor = Color.Transparent,
                        inactiveTickColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Min / Max Labels
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "-150 mV",
                        color = Color(0xFFB0B0B0),
                        fontSize = 12.sp
                    )
                    Text(
                        "150 mV",
                        color = Color(0xFFB0B0B0),
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // Bottom Helper Text
                Text(
                    text = "Changing this value may affect measurement accuracy.",
                    color = Color(0xFF9E9E9E),
                    fontSize = 12.sp
                )
            }
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun BaisPreview() {
    MaterialTheme {
        Surface {
            BaisScreen()
        }
    }
}