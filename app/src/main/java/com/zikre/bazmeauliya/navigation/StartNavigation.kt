package com.zikre.bazmeauliya.navigation

//import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.navigation.compose.composable

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.zikre.bazmeauliya.features.screens.SplashScreen
import com.zikre.bazmeauliya.utils.DataStoreUtil
import com.zikre.bazmeauliya.utils.LogoutManager

@OptIn(ExperimentalAnimationApi::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StartNavigation(context: Activity) {

//    val navController = rememberNavController()
    val navController = rememberAnimatedNavController()

    LaunchedEffect(Unit) {
        LogoutManager.logoutTrigger.collect {
            DataStoreUtil(context).saveLoggedIn(false)
            navController.navigate(NavigationScreen.Login.route) {
                popUpTo(0) { inclusive = true } // Clears all destinations from the back stack
                launchSingleTop = true
            }
        }
    }

//    NavHost(navController = navController, startDestination = NavigationScreen.Splash.route) {
    AnimatedNavHost(
        navController = navController, startDestination = NavigationScreen.Splash.route,
    ) {
        composable(NavigationScreen.Splash.route,
            enterTransition = {
                when (initialState.destination.route) {
                    NavigationScreen.Splash.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            exitTransition = {
                when (targetState.destination.route) {
                    NavigationScreen.Splash.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Left,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            },
            popEnterTransition = {
                when (initialState.destination.route) {
                    NavigationScreen.Splash.route ->
                        slideIntoContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )
                    else -> null
                }
            },
            popExitTransition = {
                when (targetState.destination.route) {
                    NavigationScreen.Splash.route ->
                        slideOutOfContainer(
                            AnimatedContentScope.SlideDirection.Right,
                            animationSpec = tween(700)
                        )

                    else -> null
                }
            }

        ) {
            SplashScreen(
                onDashboard = {
                    navController.navigate(NavigationScreen.Dashboard.route) {
                        popUpTo(NavigationScreen.Splash.route) {
                            inclusive = true
                        }
//                        launchSingleTop = true
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

     /*   composable(NavigationScreen.Login.route) {
            LoginScreen(
                viewModel = hiltViewModel(),
                onOTPScreen = {
                    navController.navigate(NavigationScreen.OTP.route)
                }
            )
        }

        composable(NavigationScreen.OTP.route) {
            OTPScreen(
                viewModel = hiltViewModel(),
                onDashboardScreen = {
                    navController.navigate(NavigationScreen.Dashboard.route) {
                        popUpTo(NavigationScreen.Dashboard.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }*/

    }
}
