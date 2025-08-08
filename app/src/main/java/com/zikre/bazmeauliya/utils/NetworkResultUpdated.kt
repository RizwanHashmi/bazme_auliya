package com.zikre.bazmeauliya.utils

sealed class NetworkResultUpdated<out T> {
    data class Success<T>(val data: T) : NetworkResultUpdated<T>()
    data class Failed<T>(val message: String?, val data: T? = null) : NetworkResultUpdated<T>()
    object Loading : NetworkResultUpdated<Nothing>()
    object Initialization : NetworkResultUpdated<Nothing>()
}