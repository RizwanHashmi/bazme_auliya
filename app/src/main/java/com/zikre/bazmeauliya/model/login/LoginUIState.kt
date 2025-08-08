package com.zikre.bazmeauliya.model.login

import androidx.compose.runtime.MutableState

data class LoginUIState(
    val name: MutableState<String>,
    val nameError: MutableState<Boolean>,
    val mobileNo: MutableState<String>,
    val mobileError: MutableState<Boolean>
)