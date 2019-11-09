package com.slice.verifly.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.slice.verifly.utility.Constants

abstract class BaseViewModel : ViewModel() , ErrorCommunicator {

    val errorLiveData = MutableLiveData<String>()
    val noInternetLiveData = MutableLiveData<String>()

    override fun notifyError(errorMessage: String, nullify: Boolean?) {
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