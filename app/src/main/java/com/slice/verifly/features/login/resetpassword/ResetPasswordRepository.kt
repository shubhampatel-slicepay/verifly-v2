package com.slice.verifly.features.login.resetpassword

import com.slice.verifly.base.BaseRepository
import com.slice.verifly.data.repo.DataManagerHelper
import com.slice.verifly.models.request.RequestModel
import com.slice.verifly.utility.Constants
import com.slice.verifly.utility.isObjectNotEmpty

class ResetPasswordRepository(private val dataManagerHelper: DataManagerHelper): BaseRepository() {

    suspend fun sendOtp(request: RequestModel): String {
        val obtainedData = dataManagerHelper.sendOtp(request)
        return if(obtainedData?.isApiCallSuccessful == true) {
            Constants.SUCCESS_RESPONSE_STATUS
        } else {
            Constants.ERROR_RESPONSE_STATUS
        }
    }

    suspend fun verifyOtp(request: RequestModel): String {
        val obtainedData = dataManagerHelper.verifyOtp(request)
        return if(obtainedData?.isApiCallSuccessful == true) {
            Constants.SUCCESS_RESPONSE_STATUS
        } else {
            Constants.ERROR_RESPONSE_STATUS
        }
    }
}