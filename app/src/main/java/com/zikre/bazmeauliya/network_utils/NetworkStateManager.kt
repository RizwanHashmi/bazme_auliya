package com.atlas.vms.network_utils

import android.util.Log
import androidx.lifecycle.MutableLiveData
import android.os.Looper
import androidx.lifecycle.LiveData


class NetworkStateManager {
    /**
     * Updates the active network status live-data
     */
    fun setNetworkConnectivityStatus(connectivityStatus: Boolean) {
        Log.d(TAG, "setNetworkConnectivityStatus() called with: connectivityStatus = [$connectivityStatus]")
        if (Looper.myLooper() == Looper.getMainLooper()) {
            activeNetworkStatusMLD.setValue(connectivityStatus)
        } else {
            activeNetworkStatusMLD.postValue(connectivityStatus)
        }
    }

    /**
     * Returns the current network status
     */
    val networkConnectivityStatus: LiveData<Boolean>
        get() {
            Log.d(TAG, "getNetworkConnectivityStatus() called")
            return activeNetworkStatusMLD
        }

    companion object {
        val TAG = NetworkStateManager::class.java.simpleName
        private var INSTANCE: NetworkStateManager? = null
        private val activeNetworkStatusMLD = MutableLiveData<Boolean>()

        @get:Synchronized
        val instance: NetworkStateManager?
            get() {
                if (INSTANCE == null) {
                    Log.d(TAG, "getInstance() called: Creating new instance")
                    INSTANCE = NetworkStateManager()
                }
                return INSTANCE
            }
    }
}