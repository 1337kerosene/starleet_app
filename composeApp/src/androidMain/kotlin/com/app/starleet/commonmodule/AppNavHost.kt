package com.app.starleet.commonmodule


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.starleet.dashboardscreens.BaisScreen
import com.app.starleet.dashboardscreens.CalibrationScreen

object Routes {
    const val DASHBOARD = "dashboard"
    const val SENSOR = "sensor"
    const val CALIBRATION = "calibration"
    const val BIAS = "bias"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost() {
    val navController = rememberNavController()


    NavHost(
        navController = navController,
        startDestination = Routes.DASHBOARD
    ) {

        composable(Routes.DASHBOARD) {

            DashboardContain(onBiasClick = {
                navController.navigate(Routes.BIAS)

            },oncalibrationClick={
                navController.navigate(Routes.CALIBRATION)

            }
                )
        }

        composable(Routes.BIAS) {

            BaisScreen(
            )
        }

        composable(Routes.CALIBRATION) {

            CalibrationScreen(
            )
        }


    }
}