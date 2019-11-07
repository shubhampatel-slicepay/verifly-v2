package com.slice.verifly.features.splash.repository

import com.slice.verifly.BuildConfig
import com.slice.verifly.base.BaseRepository
import com.slice.verifly.data.repo.DataManagerHelper
import com.slice.verifly.utility.Constants
import com.slice.verifly.utility.isObjectNotEmpty

class SplashFragmentRepository(private val dataManagerHelper: DataManagerHelper) : BaseRepository() {

    suspend fun authenticate(): String {
        val obtainedData = dataManagerHelper.authenticate()
        manager.authData(obtainedData?.body)
        return if(obtainedData?.body?.token.isObjectNotEmpty()) {
            Constants.SUCCESS_RESPONSE_STATUS
        } else {
            Constants.ERROR_RESPONSE_STATUS
        }
    }

    suspend fun checkAppVersion(): Boolean {
        val obtainedData = dataManagerHelper.checkAppVersion()
        val versionCode = obtainedData?.body?.data?.version_code
        return versionCode?.let {
            it == BuildConfig.VERSION_CODE.toString()
        } ?: false
    }

    fun checkUserLoggedInStatus(): Boolean {
        return manager.getUserLoggedInStatus()
    }
}