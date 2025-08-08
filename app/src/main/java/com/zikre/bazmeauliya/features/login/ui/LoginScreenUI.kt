package com.zikre.bazmeauliya.features.login.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
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

    //Main Column
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(top = 20.dp, bottom = 60.dp),
        verticalArrangement = Arrangement.Top
    ) {
        //Name.
        InputTextField(
            textValue = uiState.name,
            errorField = uiState.nameError,
            label = "Name*",
            leadingIcon = 0,
            textField = TEXT
        )

        //Mobile.
        InputTextField(
            textValue = uiState.mobileNo,
            errorField = uiState.mobileError,
            label = "Mobile Number*",
            leadingIcon = 0,
            textField = NUMBER
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick =onSubmitClick,
            modifier = Modifier
                .height(40.dp)
                .align(Alignment.CenterHorizontally),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text(
                text = "Submit",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )
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