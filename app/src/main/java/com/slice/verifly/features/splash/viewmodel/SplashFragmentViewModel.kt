package com.slice.verifly.features.splash.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.slice.verifly.base.BaseViewModel
import com.slice.verifly.features.splash.repository.SplashFragmentRepository
import kotlinx.coroutines.Dispatchers

class SplashFragmentViewModel(private val repository: SplashFragmentRepository) : BaseViewModel() {

    fun authenticate(): LiveData<String> {
        return liveData(Dispatchers.IO) {
            emit(repository.authenticate())
        }
    }

    fun checkAppVersion(): LiveData<Boolean> {
        return liveData(Dispatchers.IO) {
            emit(repository.checkAppVersion())
        }
    }

    fun checkUserLoggedInStatus(): Boolean {
         return repository.checkUserLoggedInStatus()
    }
}