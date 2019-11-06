package com.slice.verifly.features.splash.mainsplash

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.slice.verifly.base.BaseViewModel
import com.slice.verifly.base.ErrorCommunicator
import com.slice.verifly.utility.Constants
import kotlinx.coroutines.Dispatchers

class SplashFragmentViewModel(private val repository: SplashFragmentRepository) : BaseViewModel(), ErrorCommunicator {

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

    override fun notifyOnError(errorMessage: String, nullify: Boolean?) {
        if (nullify == true) {
            errorLiveData.value = null
        }
        errorLiveData.value = errorMessage
    }

    fun noInternet() {
        noInternetLiveData.value = null
        noInternetLiveData.value = Constants.NO_INTERNET_MESSAGE
    }
}