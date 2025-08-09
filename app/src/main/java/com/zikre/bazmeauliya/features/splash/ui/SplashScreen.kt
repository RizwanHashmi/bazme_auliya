package com.zikre.bazmeauliya.features.splash.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.zikre.bazmeauliya.utils.DataStoreUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Composable
fun SplashScreen(onHomeScreen: () -> Unit, onLogin: () -> Unit) {

    val context = LocalContext.current
    val dataStoreUtil = remember { DataStoreUtil(context) } // ✅ Remembered instance
    var hasNavigated by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        dataStoreUtil.isLoggedIn().collectLatest { isLoggedIn ->
            delay(1000)
            if (!hasNavigated) {
                hasNavigated = true
                if (isLoggedIn) onHomeScreen() else onLogin()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White,
                        Color(0xFF5EC7FF)
                    ),
                    start = Offset(1f, 0f), // top left corner
                    end = Offset(1100f, 1000f) // bottom right corner
                )
            )
    ) {
        Text(
            text = "Welcome to Project",
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}





