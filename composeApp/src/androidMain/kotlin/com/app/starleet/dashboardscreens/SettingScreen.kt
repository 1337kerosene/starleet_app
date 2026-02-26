package com.app.starleet.dashboardscreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import com.app.starleet.R

import androidx.compose.ui.unit.sp

@Composable
fun SettingScreen() {

    var isSilentMode by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000000))
            .padding(16.dp)
            .systemBarsPadding()
            .padding(bottom = 50.dp)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {

            Text(
                text = "Settings",
                color = colorResource(id = R.color.whitecolor),
                fontFamily = FontFamily(Font(R.font.manrope_bold)),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(20.dp))

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
                    .padding(horizontal = 16.dp, vertical = 18.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        // Preview-safe icon
                        Icon(
                            painter = painterResource(id = R.drawable.silent_icon),
                            contentDescription = "Silent Mode",
                            tint = Color.Unspecified, // keeps original icon color
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))

                        Column {
                            Text(
                                text = "Silent Mode",
                                color = colorResource(id = R.color.whitecolor),
                                fontFamily = FontFamily(Font(R.font.manrope_bold)),                                fontSize = 14.sp,
                            )

                            Text(
                                text = if (isSilentMode) "On" else "Off",
                                color = colorResource(id = R.color.graycolor),
                                fontFamily = FontFamily(Font(R.font.manrope_medium)),
                                fontSize = 12.sp
                            )
                        }
                    }

                    Switch(
                        checked = isSilentMode,
                        onCheckedChange = { isSilentMode = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Black,
                            checkedTrackColor = Color(0xFF1EC8A5),
                            uncheckedThumbColor = Color.White,
                            uncheckedTrackColor = Color.DarkGray
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .clip(RoundedCornerShape(30.dp))
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF3A1F1F),
                                Color(0xFF1F2A2E)
                            )
                        )
                    )
                    .padding(horizontal = 6.dp),
                contentAlignment = Alignment.CenterStart
            ) {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE53935)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "»",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        text = "Slide to delete all data",
                        color = colorResource(id = R.color.redmidfade),
                        fontFamily = FontFamily(Font(R.font.manrope_semibold)),                            fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingPreview() {
    MaterialTheme {
        Surface {
            SettingScreen()
        }
    }
}