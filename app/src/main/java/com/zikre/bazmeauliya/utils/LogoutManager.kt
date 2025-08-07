package com.zikre.bazmeauliya.utils

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object LogoutManager {

    private val _logoutTrigger = MutableSharedFlow<Unit>(replay = 0)
    val logoutTrigger = _logoutTrigger.asSharedFlow()
    suspend fun triggerLogout() {
        _logoutTrigger.emit(Unit)
    }
}