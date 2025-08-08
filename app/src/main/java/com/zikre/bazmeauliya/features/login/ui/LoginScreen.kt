package com.zikre.bazmeauliya.features.login.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.atlas.vms.network_utils.connectivityState
import com.zikre.bazmeauliya.features.login.viewModel.LoginViewModel
import com.zikre.bazmeauliya.model.login.LoginUIState
import com.zikre.bazmeauliya.network_utils.ConnectionState
import com.zikre.bazmeauliya.utils.NetworkResultUpdated

@Composable
fun LoginScreen(viewModel: LoginViewModel, onOTPScreen: () -> Unit) {
    val context = LocalContext.current
    val mobileNumber by viewModel.mobileNumber.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val loginState by viewModel.loginState.collectAsState()

    val connection by connectivityState()

    LaunchedEffect(connection) {
        viewModel.isConnected.value = connection == ConnectionState.Available
    }

    val uiState = LoginUIState(
        name = viewModel.name,
        nameError = viewModel.nameError,
        mobileNo = viewModel.mobileNo,
        mobileError = viewModel.mobileNoError
    )

    LaunchedEffect(loginState) {
        when (loginState) {
            is NetworkResultUpdated.Success -> {
                val data = (loginState as NetworkResultUpdated.Success).data
                if (data.status == 1) {
                    viewModel.setMobileNumber()
                    onOTPScreen()
                } else {
                    Toast.makeText(context, data.msg, Toast.LENGTH_LONG).show()

                }
            }

            is NetworkResultUpdated.Failed -> {
                   Toast.makeText(
                       context,
                       (loginState as NetworkResultUpdated.Failed).message,
                       Toast.LENGTH_LONG
                   ).show()
            }

            else -> Unit
        }
    }


    LoginScreenUI(
        uiState = uiState,
        onSubmitClick = {
//            viewModel.getLoginUpdated()
        }
    )
}








