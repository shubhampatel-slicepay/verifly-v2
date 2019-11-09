package com.slice.verifly.features.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.slice.verifly.base.BaseViewModel
import com.slice.verifly.features.login.repository.ChangePasswordRepository
import com.slice.verifly.models.request.RequestModel
import kotlinx.coroutines.Dispatchers

class ChangePasswordViewModel(private val repository: ChangePasswordRepository): BaseViewModel() {

    fun changePassword(password: String): LiveData<String> {
        val request = RequestModel().apply {
            this.password = password
        }
        return liveData(Dispatchers.IO) {
            emit(repository.changePassword(request))
        }
    }
}