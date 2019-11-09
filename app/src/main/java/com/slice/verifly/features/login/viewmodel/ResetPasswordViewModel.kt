package com.slice.verifly.features.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.slice.verifly.base.BaseViewModel
import com.slice.verifly.features.login.repository.ResetPasswordRepository
import com.slice.verifly.models.request.RequestModel
import kotlinx.coroutines.Dispatchers

class ResetPasswordViewModel(private val repository: ResetPasswordRepository): BaseViewModel() {

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
}