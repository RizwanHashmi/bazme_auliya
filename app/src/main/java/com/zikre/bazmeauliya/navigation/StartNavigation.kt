package com.zikre.bazmeauliya.navigation

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.zikre.bazmeauliya.features.login.ui.LoginScreen
import com.zikre.bazmeauliya.features.splash.ui.SplashScreen
import com.zikre.bazmeauliya.utils.DataStoreUtil
import com.zikre.bazmeauliya.utils.LogoutManager


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StartNavigation(context: Activity) {

    val navController = rememberNavController()
    val context = LocalContext.current

    var hasNavigated by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        LogoutManager.logoutTrigger.collect {
            if (!hasNavigated) {
                hasNavigated = true
                DataStoreUtil(context).saveLoggedIn(false)
                navController.navigate(NavigationScreen.Login.route) {
                    popUpTo(0) { inclusive = true }
                    launchSingleTop = true
                }
            }
        }
    }


    NavHost(navController = navController, startDestination = NavigationScreen.Splash.route) {
        composable(NavigationScreen.Splash.route) {
            SplashScreen(
                onDashboard = {
                    navController.navigate(NavigationScreen.Dashboard.route) {
                        popUpTo(NavigationScreen.Splash.route) {
                            inclusive = true
                        }
                    }
                },
                onLogin = {
                    navController.navigate(NavigationScreen.Login.route) {
                        popUpTo(NavigationScreen.Splash.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(NavigationScreen.Login.route) {
            LoginScreen(viewModel = hiltViewModel(), onOTPScreen = {})
        }
    }
}
