package com.zikre.bazmeauliya.features.login.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zikre.bazmeauliya.features.login.repository.LoginRepository
import com.zikre.bazmeauliya.model.login.LoginResponse
import com.zikre.bazmeauliya.model.login.MobileNoRequest
import com.zikre.bazmeauliya.utils.DataStoreUtil
import com.zikre.bazmeauliya.utils.NetworkResultUpdated
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: LoginRepository, private val dataStoreUtil: DataStoreUtil) : ViewModel() {

    var mobileNumber = MutableStateFlow("")
    var isLoading = MutableStateFlow(false)
    var isConnected = mutableStateOf(false)

    var name: MutableState<String> = mutableStateOf("")
    var nameError: MutableState<Boolean> = mutableStateOf(false)

    var mobileNo: MutableState<String> = mutableStateOf("")
    var mobileNoError: MutableState<Boolean> = mutableStateOf(false)

    private val _loginState = MutableStateFlow<NetworkResultUpdated<LoginResponse>>(NetworkResultUpdated.Initialization)
    val loginState: StateFlow<NetworkResultUpdated<LoginResponse>> get() = _loginState

    fun getLoginUpdated() {
        if (!isConnected.value) return  // Don't proceed if offline
        viewModelScope.launch {
            repository.getLoginNew(MobileNoRequest(mobileNumber.value))
                .collect { result ->
                    _loginState.value = result
                    isLoading.value = result is NetworkResultUpdated.Loading
                }
        }
    }

    fun registerLocally(onSuccess: () -> Unit) {
        val nameInput = name.value.trim()
        val mobileInput = mobileNo.value.trim()

        // Basic validation
        nameError.value = nameInput.isEmpty()
        mobileNoError.value = mobileInput.length != 10

        if (nameError.value || mobileNoError.value) return

        viewModelScope.launch {
            dataStoreUtil.setUserName(nameInput)
            dataStoreUtil.saveMobileNo(mobileInput)
            dataStoreUtil.saveLoggedIn(true)
            onSuccess()
        }
    }

    fun setMobileNumber() {
            viewModelScope.launch {
                dataStoreUtil.saveMobileNo(mobileNumber.value)
            }
    }
}