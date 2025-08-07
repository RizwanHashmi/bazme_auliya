package com.zikre.bazmeauliya.component

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zikre.bazmeauliya.R

@Composable
fun ShowAlertDialog(
    isDialogShow: Boolean,
    title: String,
    description: String,
    button: String,
    onAction: () -> Unit,
    onDismissRequest: () -> Unit
) {

    val isDialog by remember { mutableStateOf(isDialogShow) }



    if (isDialog) {

        AnimatedTransitionDialog(onDismissRequest = onDismissRequest) { dialogHelper ->
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier

            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    // Title
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = title,
                            style = TextStyle(
                                fontFamily = FontFamily(
                                    Font(
                                        resId = R.font.poppins_semibold
                                    )
                                ),
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )

                        Image(
                            painter = painterResource(id = R.drawable.ic_trailling),
                            "",
                            modifier = Modifier
                                .size(24.dp)
                                .clickable {
                                    dialogHelper::triggerAnimatedDismiss.invoke()
                                }
                        )
                    }

                    Text(
                        text = description,
                        style = TextStyle(
                            fontFamily = FontFamily(
                                Font(
                                    resId = R.font.poppins_regular
                                )
                            ),
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )

                    Button(
                        onClick = {
                            onAction.invoke()
                            dialogHelper::triggerAnimatedDismiss.invoke()
                        }
                    ) {
                        Text(text = button)
                    }
                }
            }
        }
    }
}



