package com.zikre.bazmeauliya.features.home

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
fun HomeScreen(viewModel: LoginViewModel, onHomeScreen: () -> Unit) {
    val context = LocalContext.current

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

    HomeScreenUI(
        uiState = uiState,
        onSubmitClick = {
            viewModel.registerLocally {
                Toast.makeText(context, "Registered successfully", Toast.LENGTH_SHORT).show()
                onHomeScreen()
            }
        }
    )
}








