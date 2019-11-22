package com.slice.verifly.features.login.repository

import com.slice.verifly.base.BaseRepository
import com.slice.verifly.models.loginrequest.LoginRequestModel
import com.slice.verifly.data.repo.DataManagerHelper
import com.slice.verifly.utility.AppUtils
import com.slice.verifly.utility.Constants

class LoginFragmentRepository(private val dataManagerHelper: DataManagerHelper) : BaseRepository() {

    suspend fun logIn(loginRequest: LoginRequestModel): String {
        val obtainedData = dataManagerHelper.logIn(loginRequest)
        manager.loginData(loginRequest.userid, obtainedData?.body?.userToken, obtainedData?.body?.data)
        return if(AppUtils.isObjectNotEmpty(obtainedData?.body?.userToken)) {
            Constants.SUCCESS_RESPONSE_STATUS
        } else {
            Constants.ERROR_RESPONSE_STATUS
        }
    }
}