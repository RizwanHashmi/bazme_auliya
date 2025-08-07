package com.zikre.bazmeauliya.di

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.zikre.bazmeauliya.utils.DataStoreUtil
import com.zikre.bazmeauliya.utils.LogoutManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.Interceptor
import okhttp3.Response
import org.json.JSONObject
import javax.inject.Inject

class TokenInterceptor @Inject constructor(
    @ApplicationContext private val context: Context
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val excludedPaths = listOf(
            "/walkinform/api/loginOTP",       // Adjust to your actual API endpoint
            "/walkinform/api/otpVerification"      // Adjust as needed
        )

        val shouldExcludeAuth = excludedPaths.any { path ->
            originalRequest.url.encodedPath.endsWith(path, ignoreCase = true)
        }

        val token = if (!shouldExcludeAuth) {
            runBlocking {
                DataStoreUtil(context).getJWT().firstOrNull()
//                "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9"
//                Log.e("MainViewModel", "UserName $token")
            }
        } else null

        val newRequest = if (token != null && token != "") {
            originalRequest.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        } else {
            originalRequest
        }

        val response = chain.proceed(newRequest)

        if (response.code == 401) {
            val errorBody = response.peekBody(Long.MAX_VALUE).string() // Safe read
            try {
                val jsonObject = JSONObject(errorBody)
                val msg = jsonObject.optString("msg", "Unauthorized")
                Log.e("Unauthorized", msg)
                CoroutineScope(Dispatchers.IO).launch {
                    LogoutManager.triggerLogout() // Emit global logout event
                    // Show toast on UI thread (optional)
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "You have been logged out: $msg", Toast.LENGTH_LONG).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Unauthorized", "Failed to parse error body")
            }
        }
        return response
    }
}