package com.slice.verifly.features.home.viewmodel

import com.slice.verifly.base.BaseViewModel
import com.slice.verifly.base.ErrorCommunicator
import com.slice.verifly.features.home.repository.HomeActivityRepository
import com.slice.verifly.utility.Constants

class HomeActivityViewModel(private val repository: HomeActivityRepository): BaseViewModel(), ErrorCommunicator {

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