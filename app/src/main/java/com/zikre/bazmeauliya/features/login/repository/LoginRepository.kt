package com.zikre.bazmeauliya.features.login.repository

import com.zikre.bazmeauliya.api.ApiInterface
import com.zikre.bazmeauliya.model.login.LoginResponse
import com.zikre.bazmeauliya.model.login.MobileNoRequest
import com.zikre.bazmeauliya.utils.NetworkResultUpdated
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepository @Inject constructor(private val postApi : ApiInterface) {
    fun getLoginNew(request: MobileNoRequest): Flow<NetworkResultUpdated<LoginResponse>> = flow {
        emit(NetworkResultUpdated.Loading)  // ✅ Emit loading state
        try {
            val response = postApi.loginWithMobile(request)

            if (response.code() == 401) return@flow

            if (response.isSuccessful && response.body() != null) {
                emit(NetworkResultUpdated.Success(response.body()!!))
            } else {
                emit(NetworkResultUpdated.Failed("Something went wrong"))
            }
        } catch (e: Exception) {
            emit(NetworkResultUpdated.Failed(e.localizedMessage ?: "Unknown error"))
        }
    }.flowOn(Dispatchers.IO)
}