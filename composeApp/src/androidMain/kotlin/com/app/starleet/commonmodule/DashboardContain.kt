package com.app.starleet.commonmodule

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.app.starleet.R
import com.app.starleet.dashboardscreens.CalendarScreen
import com.app.starleet.dashboardscreens.HomeScreen
import com.app.starleet.dashboardscreens.ScanScreen
import com.app.starleet.dashboardscreens.SensorScreen
import com.app.starleet.dashboardscreens.SettingScreen


@RequiresApi(Build.VERSION_CODES.O)
@Composable

fun DashboardContain(onBiasClick: () -> Unit, oncalibrationClick: () -> Unit) {

    var selectedIndex by remember { mutableStateOf(0) }

    val items = listOf(
        BottomNavigationItemData("Home", R.drawable.icon_home, R.drawable.icon_home),
        BottomNavigationItemData("Calendar", R.drawable.icon_calendar, R.drawable.icon_calendar),
        BottomNavigationItemData("Profile", R.drawable.icon_wifi, R.drawable.icon_wifi),
        BottomNavigationItemData("Profile", R.drawable.icon_sensor, R.drawable.icon_sensor),
        BottomNavigationItemData("Profile", R.drawable.icon_setting, R.drawable.icon_setting),
    )

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            CustomBottomBar(
                items = items,
                selectedIndex = selectedIndex,
                onItemSelected = { selectedIndex = it }
            )
        }
    ) { padding ->
        when (selectedIndex) {
            0 -> HomeScreen()
            1 -> CalendarScreen()
            2 -> ScanScreen()
            3 -> SensorScreen(onBiasClick = onBiasClick, oncalibrationClick = oncalibrationClick)
            4 -> SettingScreen()
        }
    }
}

