package com.atlas.vms.network_utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class NetworkConnectionInterceptor(context: Context) : Interceptor {

    private val applicationContext = context.applicationContext

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInternetAvailable()) {
            val requ = chain.request()
            Log.e("NetworkError", "hit $requ")
//            throw  NoInternetException("We can serve you better once you have your internet signal back")
        }
        return chain.proceed(chain.request())
    }

    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager = applicationContext.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager?
        connectivityManager.let {
            it?.getNetworkCapabilities(connectivityManager?.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
        return result
    }
}