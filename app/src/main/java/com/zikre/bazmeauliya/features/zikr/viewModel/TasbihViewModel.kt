package com.zikre.bazmeauliya.features.zikr.viewModel

import androidx.lifecycle.ViewModel
import com.zikre.bazmeauliya.features.login.repository.LoginRepository
import com.zikre.bazmeauliya.utils.DataStoreUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TasbihViewModel @Inject constructor(private val repository: LoginRepository, private val dataStoreUtil: DataStoreUtil) : ViewModel() {


}