package com.app.starleet.commonmodule


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.app.starleet.dashboardscreens.BaisScreen
import com.app.starleet.dashboardscreens.CalibrationScreen

object Routes {
    const val DASHBOARD_GRAPH = "dashboard_graph"

    const val DASHBOARD = "dashboard"
    const val SENSOR = "sensor"
    const val CALIBRATION = "calibration"
    const val BIAS = "bias"
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    var selectedIndex by rememberSaveable { mutableStateOf(0) }

    NavHost(
        navController = navController,
        startDestination = Routes.DASHBOARD_GRAPH
    ) {

        navigation(
            startDestination = Routes.DASHBOARD,
            route = Routes.DASHBOARD_GRAPH
        ) {

            composable(Routes.DASHBOARD) {

                DashboardContain(
                    selectedIndex = selectedIndex,
                    onTabSelected = { selectedIndex = it },

                    onBiasClick = {
                        navController.navigate(Routes.BIAS)
                    },

                    oncalibrationClick = {
                        navController.navigate(Routes.CALIBRATION)
                    }
                )
            }
        }

        composable(Routes.BIAS) {

            BaisScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composable(Routes.CALIBRATION) {

            CalibrationScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}