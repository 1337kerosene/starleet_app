package com.app.starleet.dashboardscreens

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.starleet.R
import com.app.starleet.intent.LactateIntent
import com.app.starleet.viewmodel.LactateViewModel
import kotlinx.coroutines.delay

@Composable
fun ScanScreen(viewModel: LactateViewModel) {

    var isScanning by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0F0F0F))
            .systemBarsPadding()
            .padding(16.dp)
            .padding(bottom = 50.dp)
    ) {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Scan Lactate",
                modifier = Modifier.align(Alignment.Start),
                color = colorResource(id = R.color.whitecolor),
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = FontFamily(Font(R.font.manrope_bold))
                )
            )

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "0.1 mM",
                color = colorResource(id = R.color.lightskyblue),
                fontFamily = FontFamily(Font(R.font.manrope_semibold)),
                fontSize = 26.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Lactate  (No Sweat)",
                color = colorResource(id = R.color.graycolor),
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(100.dp))

            PulseAnimation(isScanning)

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    isScanning = true

                    viewModel.processIntent(
                        LactateIntent.AddScan(
                            value = 2.9,
                            sweat = "No Sweat",
                            change = 0.3
                        )
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .clip(RoundedCornerShape(10.dp)),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2BA39A)
                )
            ) {
                Text(
                    text = if (isScanning) "Scanning..." else "Start Scan",
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }

    LaunchedEffect(isScanning) {
        if (isScanning) {
            delay(2000)
            isScanning = false
        }
    }
}

@Composable
fun PulseAnimation(isRunning: Boolean) {

    if (!isRunning) {
        Box(
            Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(Color(0xFF2BA39A).copy(alpha = 0.35f))
        )
        return
    }

    val infinite = rememberInfiniteTransition(label = "")

    val scale1 by infinite.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.6f,
        animationSpec = infiniteRepeatable(
            tween(1500),
            RepeatMode.Restart
        ), label = ""
    )

    val scale2 by infinite.animateFloat(
        initialValue = 0.6f,
        targetValue = 1.8f,
        animationSpec = infiniteRepeatable(
            tween(1500, delayMillis = 300),
            RepeatMode.Restart
        ), label = ""
    )

    val scale3 by infinite.animateFloat(
        initialValue = 0.8f,
        targetValue = 2f,
        animationSpec = infiniteRepeatable(
            tween(1500, delayMillis = 600),
            RepeatMode.Restart
        ), label = ""
    )

    val green = Color(0xFF2BA39A)

    Box(contentAlignment = Alignment.Center) {

        PulseCircle(scale1, green.copy(alpha = 0.25f))
        PulseCircle(scale2, green.copy(alpha = 0.18f))
        PulseCircle(scale3, green.copy(alpha = 0.12f))

        Box(
            Modifier
                .size(120.dp)
                .clip(CircleShape)
                .background(green.copy(alpha = 0.35f))
        )
    }
}

@Composable
private fun PulseCircle(scale: Float, color: Color) {
    Box(
        Modifier
            .size(160.dp)
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .clip(CircleShape)
            .background(color)
    )
}

/*
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ScanPreview() {
    MaterialTheme {
        ScanScreen()
    }
}*/
