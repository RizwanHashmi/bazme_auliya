package com.zikre.bazmeauliya.network_utils

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}