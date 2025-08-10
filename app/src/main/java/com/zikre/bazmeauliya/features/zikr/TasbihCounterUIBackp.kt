package com.zikre.bazmeauliya.features.zikr

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zikre.bazmeauliya.component.CircularProgressBar

@Composable
fun TasbihCounterUIBackup(
    count: Int,
    onTasbihClick: () -> Unit,
    onResetClick: () -> Unit
) {

    val progress = (count.coerceIn(10, 100)) / 100f
    val progressColor = when {
        progress < 0.33f -> Color(0xFF81C784)
        progress < 0.66f -> Color(0xFF4CAF50)
        else -> Color(0xFF2E7D32)
    }

    val trackColor = when {
        else -> Color(0xFF2E7D32)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFFF1F8E9), Color(0xFFE8F5E9))))
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Tasbih Counter",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF2E7D32)
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Circular Progress with Transparent Center
            Box(
                modifier = Modifier
                    .size(220.dp),
                contentAlignment = Alignment.Center
            ) {
                // Progress Ring
                CircularProgressBar(
                    progress = progress,
                    modifier = Modifier.size(220.dp),
                    progressColor = progressColor,
                    trackColor = Color(0xFFE0E0E0), // Light gray
                    strokeWidth = 5.dp
                )

                // Transparent Center with Count
                Box(
                    modifier = Modifier
                        .size(160.dp)
                        .clip(CircleShape)
                        .clickable { onTasbihClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = count.toString().padStart(2, '0'),
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E7D32)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Reset Button
            OutlinedButton(
                onClick = onResetClick,
                border = BorderStroke(2.dp, Color(0xFF388E3C)),
                shape = RoundedCornerShape(50),
                modifier = Modifier
                    .height(48.dp)
                    .width(140.dp)
            ) {
                Text(
                    text = "Reset",
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF388E3C)
                    )
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun TasbihCounterBackupPreview() {
    var count by remember { mutableStateOf(30) }

    TasbihCounterUIBackup(
        count = count,
        onTasbihClick = {
            if (count < 100) count++
        },
        onResetClick = { count = 0 }
    )
}