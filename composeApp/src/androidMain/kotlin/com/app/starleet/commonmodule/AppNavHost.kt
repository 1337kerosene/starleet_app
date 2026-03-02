package com.app.starleet.commonmodule


import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.app.starleet.dashboardscreens.BaisScreen
import com.app.starleet.dashboardscreens.CalibrationScreen
import com.app.starleet.roomdb.AppDatabase
import com.app.starleet.roomdb.LactateRepository
import com.app.starleet.viewmodel.LactateViewModel

object Routes {
    const val DASHBOARD_GRAPH = "dashboard_graph"

    const val DASHBOARD = "dashboard"
    const val SENSOR = "sensor"
    const val CALIBRATION = "calibration"
    const val BIAS = "bias"
}

@SuppressLint("ViewModelConstructorInComposable")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavHost() {
    val context = LocalContext.current
    val application = context.applicationContext as Application


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
                val database = AppDatabase.getDatabase(application)
                val repository = LactateRepository(database.lactateDao())
                val viewModel = LactateViewModel(repository,application)


                DashboardContain(
                    selectedIndex = selectedIndex,
                    onTabSelected = { selectedIndex = it },

                    onBiasClick = {
                        navController.navigate(Routes.BIAS)
                    },

                    oncalibrationClick = {
                        navController.navigate(Routes.CALIBRATION)
                    },viewModel
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