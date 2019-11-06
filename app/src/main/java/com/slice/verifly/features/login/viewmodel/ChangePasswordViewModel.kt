package com.slice.verifly.features.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.slice.verifly.base.BaseViewModel
import com.slice.verifly.base.ErrorCommunicator
import com.slice.verifly.features.login.repository.ChangePasswordRepository
import com.slice.verifly.models.request.RequestModel
import com.slice.verifly.utility.Constants
import kotlinx.coroutines.Dispatchers

class ChangePasswordViewModel(private val repository: ChangePasswordRepository): BaseViewModel(), ErrorCommunicator {

    fun changePassword(password: String): LiveData<String> {
        val request = RequestModel().apply {
            this.password = password
        }
        return liveData(Dispatchers.IO) {
            emit(repository.changePassword(request))
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