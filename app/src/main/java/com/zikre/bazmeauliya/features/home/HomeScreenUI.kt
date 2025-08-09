package com.zikre.bazmeauliya.features.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
fun HomeScreenUI(
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
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = "Successfully Registered",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {

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

    HomeScreenUI(
        uiState = uiState,
        onSubmitClick = {}
    )
}