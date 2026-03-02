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
import com.app.starleet.viewmodel.LactateViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashboardContain(
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    onBiasClick: () -> Unit,
    oncalibrationClick: () -> Unit,
    viewModel: LactateViewModel
) {

    val items = listOf(
        BottomNavigationItemData("Home", R.drawable.icon_home, R.drawable.icon_home),
        BottomNavigationItemData("Calendar", R.drawable.icon_calendar, R.drawable.icon_calendar),
        BottomNavigationItemData("Profile", R.drawable.icon_wifi, R.drawable.icon_wifi),
        BottomNavigationItemData("Setting", R.drawable.icon_setting, R.drawable.icon_setting),
    )

    Scaffold(
        containerColor = Color.Transparent,
        bottomBar = {
            CustomBottomBar(
                items = items,
                selectedIndex = selectedIndex,
                onItemSelected = { onTabSelected(it) }
            )
        }
    ) { padding ->

        when (selectedIndex) {
            0 -> HomeScreen(viewModel)
            1 -> CalendarScreen(viewModel)
            2 -> ScanScreen(viewModel)
            3 -> SensorScreen(
                onBiasClick = onBiasClick,
                oncalibrationClick = oncalibrationClick
            )
        }
    }
}

