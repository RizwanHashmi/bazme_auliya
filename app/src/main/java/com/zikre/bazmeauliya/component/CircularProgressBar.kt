package com.zikre.bazmeauliya.component


import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.foundation.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressBar(
    progress: Float,
    modifier: Modifier = Modifier,
    progressColor: Color,
    trackColor: Color = Color(0xFFE0E0E0),
    strokeWidth: Dp = 5.dp
) {
    Canvas(modifier = modifier) {
        val stroke = strokeWidth.toPx()

        // Track circle
        drawArc(
            color = trackColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = stroke)
        )

        // Progress arc
        drawArc(
            color = progressColor,
            startAngle = -90f,
            sweepAngle = 360 * progress,
            useCenter = false,
            style = Stroke(width = stroke)
        )
    }
}
