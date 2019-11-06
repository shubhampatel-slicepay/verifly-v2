package com.slice.verifly.features.login.changepassword

import com.slice.verifly.base.BaseRepository
import com.slice.verifly.data.repo.DataManagerHelper
import com.slice.verifly.models.request.RequestModel
import com.slice.verifly.utility.Constants

class ChangePasswordRepository(private val dataManagerHelper: DataManagerHelper): BaseRepository() {

    suspend fun changePassword(request: RequestModel): String {
        val obtainedData = dataManagerHelper.changePassword(request)
        return if(obtainedData?.isApiCallSuccessful == true) {
            Constants.SUCCESS_RESPONSE_STATUS
        } else {
            Constants.ERROR_RESPONSE_STATUS
        }
    }
}