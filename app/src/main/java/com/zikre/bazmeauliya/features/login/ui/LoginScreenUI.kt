package com.zikre.bazmeauliya.features.login.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.zikre.bazmeauliya.component.InputTextField
import com.zikre.bazmeauliya.model.login.LoginUIState
import com.zikre.bazmeauliya.utils.Constants.NUMBER
import com.zikre.bazmeauliya.utils.Constants.TEXT

@Composable
fun LoginScreenUI(
    uiState: LoginUIState,
    onSubmitClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .imePadding(), // 👈 This pushes content above the keyboard
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Zikr Registration",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Subtitle
            Text(
                text = "Begin your spiritual journey with remembrance",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.secondary
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Name Field
            InputTextField(
                textValue = uiState.name,
                errorField = uiState.nameError,
                label = "Name*",
                leadingIcon = 0,
                textField = TEXT
            )

            // Mobile Field
            InputTextField(
                textValue = uiState.mobileNo,
                errorField = uiState.mobileError,
                label = "Mobile Number*",
                leadingIcon = 0,
                textField = NUMBER
            )

            Spacer(modifier = Modifier.height(40.dp))

            // Submit Button
            Button(
                onClick = onSubmitClick,
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
                shape = RoundedCornerShape(16.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "Submit",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {

    val mockName = remember { mutableStateOf("Rizwan") }
    val mockError = remember { mutableStateOf(false) }

    val mockMobileNo = remember { mutableStateOf("8237763775") }
    val mockMobileNoError = remember { mutableStateOf(false) }

    val uiState = LoginUIState(
        name = mockName,
        nameError = mockError,
        mobileNo = mockMobileNo,
        mobileError = mockMobileNoError
    )

    LoginScreenUI(
        uiState = uiState,
        onSubmitClick = {}
    )
}