package com.slice.verifly.features.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.slice.verifly.base.BaseViewModel
import com.slice.verifly.base.ErrorCommunicator
import com.slice.verifly.features.login.repository.ResetPasswordRepository
import com.slice.verifly.models.request.RequestModel
import com.slice.verifly.utility.Constants
import kotlinx.coroutines.Dispatchers

class ResetPasswordViewModel(private val repository: ResetPasswordRepository): BaseViewModel(), ErrorCommunicator {

    fun sendOtp(phoneNumber: String): LiveData<String> {
        val request = RequestModel().apply {
            phone = phoneNumber
        }
        return liveData(Dispatchers.IO) {
            emit(repository.sendOtp(request))
        }
    }

    fun verifyOtp(phoneNumber: String, otpNumber: String): LiveData<String> {
        val request = RequestModel().apply {
            phone = phoneNumber
            otp = otpNumber
        }
        return liveData(Dispatchers.IO) {
            emit(repository.verifyOtp(request))
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