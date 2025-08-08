package com.zikre.bazmeauliya.features.splash.view_model

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zikre.bazmeauliya.utils.Constants.TAG
import com.zikre.bazmeauliya.utils.DataStoreUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val dataStoreUtil: DataStoreUtil) : ViewModel() {

    var isLoggedIn = mutableStateOf(false)

    init {
        Log.e(TAG, "INit SplashViewModel")
        getUserLoggedIn()
    }

    private fun getUserLoggedIn() {
            viewModelScope.launch {
                dataStoreUtil.isLoggedIn().collectLatest {
                    isLoggedIn.value = it
                }
            }
    }
}