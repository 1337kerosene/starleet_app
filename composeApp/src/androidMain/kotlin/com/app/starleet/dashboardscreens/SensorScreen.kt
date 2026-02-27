package com.app.starleet.dashboardscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.app.starleet.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

@Composable
fun SensorScreen(onBiasClick: () -> Unit, oncalibrationClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
            .padding(14.dp)
            .systemBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "Sensor Calibration",
            color = colorResource(id = R.color.whitecolor),
            fontFamily = FontFamily(Font(R.font.manrope_bold)),
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(16.dp))

        ButtonWithArrow(
            label = "Bias",
            onClick = {onBiasClick() }
        )

        ButtonWithArrow(
            label = "Calibration",
            onClick = {oncalibrationClick() }


        )
    }
}
@Composable
fun ButtonWithArrow(label: String, onClick: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF1B1E21),
                        Color(0xFF1F2F2F)
                    )
                ),
                shape = RoundedCornerShape(12.dp)
            )
    ) {

        Button(
            onClick = onClick,
            shape = RoundedCornerShape(20.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    color = colorResource(id = R.color.whitecolor),
                    fontFamily = FontFamily(Font(R.font.manrope_semibold)),                )

                Icon(
                    painter = painterResource(id = R.drawable.graterthenbig_icon),
                    contentDescription = "Arrow",
                    tint = Color.White
                )
            }
        }
    }
}