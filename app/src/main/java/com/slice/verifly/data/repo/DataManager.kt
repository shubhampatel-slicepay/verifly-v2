package com.slice.verifly.data.repo

import com.google.gson.Gson
import com.slice.verifly.base.ApiResponse
import com.slice.verifly.data.local.pref.AppPreferences
import com.slice.verifly.data.remote.Api
import com.slice.verifly.models.loginrequest.LoginRequestModel
import com.slice.verifly.models.loginresponse.LoginResponseModel
import com.slice.verifly.models.request.RequestModel
import com.slice.verifly.models.response.ResponseModel
import com.slice.verifly.models.tasks.UserTasksModel
import com.slice.verifly.models.version.VersionModel
import com.slice.verifly.utility.SlicePayLog
import kotlinx.coroutines.CoroutineScope
import org.koin.core.KoinComponent
import org.koin.core.inject

class DataManager : DataManagerHelper, KoinComponent {

    companion object {
        private val TAG = "DataManager"
        private const val EXCEPTION = "Exception : "
    }

    /**
     * components
     */

    private val api: Api by inject()
    private val preferences: AppPreferences by inject()
    private val gson: Gson by inject()
    private val coroutineScope: CoroutineScope by inject()

    /**
     * implementations
     */

    override suspend fun authenticate(): ApiResponse<ResponseModel>? {
        return try {
            ApiResponse(api.authenticate())
        } catch (e: Exception) {
            SlicePayLog.info(TAG, EXCEPTION.plus(e.message))
            null
        }
    }

    override suspend fun checkAppVersion(): ApiResponse<VersionModel>? {
        return try {
            ApiResponse(api.getVersionCode())
        } catch (e: Exception) {
            SlicePayLog.info(TAG, EXCEPTION.plus(e.message))
            null
        }
    }

    override suspend fun logIn(loginRequest: LoginRequestModel): ApiResponse<LoginResponseModel>? {
        return try {
            ApiResponse(api.logIn(loginRequest))
        } catch (e: Exception) {
            SlicePayLog.info(TAG, EXCEPTION.plus(e.message))
            null
        }
    }

    override suspend fun sendOtp(request: RequestModel): ApiResponse<ResponseModel>? {
        return try {
            ApiResponse(api.sendOTP(request))
        } catch (e: Exception) {
            SlicePayLog.info(TAG, EXCEPTION.plus(e.message))
            null
        }
    }

    override suspend fun verifyOtp(request: RequestModel): ApiResponse<LoginResponseModel>? {
        return try {
            ApiResponse(api.verifyOTP(request))
        } catch (e: Exception) {
            SlicePayLog.info(TAG, EXCEPTION.plus(e.message))
            null
        }
    }

    override suspend fun changePassword(request: RequestModel): ApiResponse<ResponseModel>? {
        return try {
            ApiResponse(api.changePassword(request))
        } catch (e: Exception) {
            SlicePayLog.info(TAG, EXCEPTION.plus(e.message))
            null
        }
    }

    override suspend fun getAssignedTasks(): ApiResponse<UserTasksModel>? {
        return try {
            ApiResponse(api.getAssignedTasks())
        } catch (e: Exception) {
            SlicePayLog.info(TAG, EXCEPTION.plus(e.message))
            null
        }
    }
}