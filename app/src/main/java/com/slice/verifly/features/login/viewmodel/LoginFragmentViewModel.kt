package com.slice.verifly.features.login.viewmodel

import android.annotation.SuppressLint
import android.os.Build
import android.telephony.TelephonyManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.slice.verifly.base.BaseViewModel
import com.slice.verifly.base.ErrorCommunicator
import com.slice.verifly.features.login.repository.LoginFragmentRepository
import com.slice.verifly.models.loginrequest.DeviceData
import com.slice.verifly.models.loginrequest.LoginRequestModel
import com.slice.verifly.models.loginrequest.SessionData
import com.slice.verifly.utility.AppUtils
import com.slice.verifly.utility.Constants
import com.slice.verifly.utility.SlicePayLog
import kotlinx.coroutines.Dispatchers

class LoginFragmentViewModel(private val repository: LoginFragmentRepository) : BaseViewModel(), ErrorCommunicator {

    private val TAG = "LoginFragmentViewModel"

    @SuppressLint("MissingPermission", "HardwareIds")
    fun logIn(
        userNumber: String,
        userPassword: String,
        tm: TelephonyManager
    ): LiveData<String> {
        var deviceData: DeviceData? = null
        var sessionData: SessionData? = null
        try {
            deviceData = DeviceData(
                tm.deviceId,
                tm.simSerialNumber,
                "mobile",
                tm.networkOperatorName,
                Build.VERSION.RELEASE,
                AppUtils.getDeviceName(),
                "android",
                null
            )
            sessionData = SessionData(
                "android",
                AppUtils.getCurrentTimeStamp()
            )
        } catch (e: Exception) {
            SlicePayLog.info(TAG, e.message)
        }
        val loginRequest = LoginRequestModel(
            userNumber,
            userPassword,
            deviceData,
            sessionData
        )
        return liveData(Dispatchers.IO) {
            emit(repository.logIn(loginRequest))
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