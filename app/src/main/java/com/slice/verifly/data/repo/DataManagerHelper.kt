package com.slice.verifly.data.repo

import com.slice.verifly.base.ApiResponse
import com.slice.verifly.models.loginrequest.LoginRequestModel
import com.slice.verifly.models.loginresponse.LoginResponseModel
import com.slice.verifly.models.request.RequestModel
import com.slice.verifly.models.response.ResponseModel
import com.slice.verifly.models.tasks.UserTasksModel
import com.slice.verifly.models.version.VersionModel

interface DataManagerHelper {

    suspend fun authenticate(): ApiResponse<ResponseModel>?

    suspend fun checkAppVersion(): ApiResponse<VersionModel>?

    suspend fun logIn(loginRequest: LoginRequestModel): ApiResponse<LoginResponseModel>?

    suspend fun sendOtp(request: RequestModel): ApiResponse<ResponseModel>?

    suspend fun verifyOtp(request: RequestModel): ApiResponse<LoginResponseModel>?

    suspend fun changePassword(request: RequestModel): ApiResponse<ResponseModel>?

    suspend fun getAssignedTasks(): ApiResponse<UserTasksModel>?
}