package com.zikre.bazmeauliya.features.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.zikre.bazmeauliya.utils.DataStoreUtil
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(onDashboard: () -> Unit, onLogin: () -> Unit) {

    val context = LocalContext.current
    val dataStoreUtil = remember { DataStoreUtil(context) } // ✅ Remembered instance


    LaunchedEffect(Unit) { // ✅ Ensures effect runs only once
        dataStoreUtil.isLoggedIn().collectLatest {
            delay(1000) // Simulating splash delay
            if (it) onDashboard() else onLogin()
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
       /* Image(
            painter = painterResource(
                id =
                R.drawable.img_splash_top_circle
            ),
            contentDescription = "Top End Circle",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 100.dp)
        )*/

       /* Image(
            painter = painterResource(id = R.drawable.ic_vms_splash),
            contentDescription = "ManLaptop",
            modifier = Modifier
                .align(Alignment.Center)
        )
        Image(
            painter = painterResource(id = R.drawable.img_splash_bottom_circle),
            contentDescription = "Circle",
            modifier = Modifier
                .align(Alignment.BottomStart)
        )*/
    }
}





