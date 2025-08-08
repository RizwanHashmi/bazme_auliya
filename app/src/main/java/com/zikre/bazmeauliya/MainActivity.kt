package com.zikre.bazmeauliya

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
//import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.zikre.bazmeauliya.navigation.StartNavigation
import com.zikre.bazmeauliya.ui.theme.BATheme
import com.zikre.bazmeauliya.utils.Constants.TAG
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity(), InstallStateUpdatedListener {

    private lateinit var appUpdateManager: AppUpdateManager
    private val MY_REQUEST_CODE = 500

    private var currentType = AppUpdateType.FLEXIBLE
    private lateinit var isShowSnackbar: MutableState<Boolean>

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        appUpdateManager = AppUpdateManagerFactory.create(this)
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            // Check if update is available
            if (info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) { // UPDATE IS AVAILABLE
                val update_priority = info.updatePriority()
                Log.e("inAppUpdate", "Update Available $update_priority")
                startUpdate(info, AppUpdateType.FLEXIBLE)
            } else {
                Log.e("inAppUpdate", "Update NOT Available")
                // UPDATE IS NOT AVAILABLE
            }
        }
        appUpdateManager.registerListener(this)
        setContent {
            CompositionLocalProvider(
                LocalDensity provides Density(
                    density = LocalDensity.current.density,
                    fontScale = 1f // Forces font size to remain fixed regardless of device settings
                )
            ) {
                val snackbarHostState = remember { SnackbarHostState() }
                isShowSnackbar = remember { mutableStateOf(false) }
                val scope = rememberCoroutineScope()
                BATheme() {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background,
                        contentColor = contentColorFor(MaterialTheme.colorScheme.background),
                        snackbarHost = { SnackbarHost(snackbarHostState) }
                    ) {
                        if (isShowSnackbar.value) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 20.dp),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                scope.launch {
                                    val snackbarResult = snackbarHostState.showSnackbar(
                                        message = "An update has just been downloaded.",
                                        actionLabel = "RESTART"
                                    )

                                    when (snackbarResult) {
                                        SnackbarResult.Dismissed -> {
                                            Log.d(TAG, "Dismissed")
                                        }

                                        SnackbarResult.ActionPerformed -> {
                                            appUpdateManager.completeUpdate()
                                            Log.d(
                                                "SnackbarDemo",
                                                "Snackbar's button clicked"
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        StartNavigation(this)
                    }
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        inAppUpdate.onActivityResult(requestCode, resultCode, data)
//        inAppUpdate.setOnCallBack(this)
        if (resultCode != RESULT_OK) {
            try {
                val pInfo: PackageInfo = this.packageManager.getPackageInfo(packageName, 0)
                val version = pInfo.versionName
//                Toast.makeText(this, "Version $version", Toast.LENGTH_LONG).show()
                /* if (isOnline(this)) {
                 getAppVersion(version.toString())
             } else {
                 noInternetDialogMsg(this@MainActivity, getString(R.string.check_internet_connection))
             }*/
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this, "Version Couldn't find", Toast.LENGTH_LONG).show()
            }
        }

        /*   if (requestCode == MY_REQUEST_CODE) {
           if (resultCode != AppCompatActivity.RESULT_OK) {
               // If the update is cancelled or fails, you can request to start the update again.
               Log.e("ERROR", "Update flow failed! Result code: $resultCode")
               Toast.makeText(this, "ERROR Update flow failed! Result code: $resultCode", Toast.LENGTH_LONG).show()
           }
       }*/
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            if (currentType == AppUpdateType.FLEXIBLE) {
                // If the update is downloaded but not installed, notify the user to complete the update.
                if (info.installStatus() == InstallStatus.DOWNLOADED)
//                    flexibleUpdateDownloadCompleted()
                    isShowSnackbar.value = true
            } else if (currentType == AppUpdateType.IMMEDIATE) {
                // for AppUpdateType.IMMEDIATE only, already executing updater
                if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    startUpdate(info, AppUpdateType.IMMEDIATE)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        appUpdateManager.unregisterListener(this)
    }

    override fun onStateUpdate(state: InstallState) {
        if (state.installStatus() == InstallStatus.DOWNLOADED) {
//            flexibleUpdateDownloadCompleted()
            isShowSnackbar.value = true
        }
    }

    private fun startUpdate(info: AppUpdateInfo, type: Int) {
        appUpdateManager.startUpdateFlowForResult(info, type, this, MY_REQUEST_CODE)
        currentType = type
    }
}