package com.slice.verifly.data.remote

import com.slice.verifly.models.earningstructure.EarningStructureModel
import com.slice.verifly.models.loginrequest.LoginRequestModel
import com.slice.verifly.models.loginresponse.LoginResponseModel
import com.slice.verifly.models.rating.RatingModel
import com.slice.verifly.models.reasons.ReasonsModel
import com.slice.verifly.models.request.RequestModel
import com.slice.verifly.models.response.ResponseModel
import com.slice.verifly.models.tasks.UserTasksModel
import com.slice.verifly.models.tasksubmit.RequestTaskSubmitModel
import com.slice.verifly.models.version.VersionModel
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @POST(ApiEndPoints.INTERN_AUTHENTICATION)
    @Headers(ApiEndPoints.AUTH, ApiEndPoints.CONTENT)
    suspend fun authenticate(): Response<ResponseModel>

    @POST(ApiEndPoints.INTERN_LOG_IN)
    @Headers(ApiEndPoints.AUTH, ApiEndPoints.CONTENT, ApiEndPoints.ACCEPT)
    suspend fun logIn(@Body request: LoginRequestModel): Response<LoginResponseModel>

    @POST(ApiEndPoints.INTERN_ACCESS_REQUEST)
    @Headers(ApiEndPoints.AUTH, ApiEndPoints.CONTENT, ApiEndPoints.ACCEPT)
    suspend fun requestAccess(@Body request: RequestModel): Response<LoginResponseModel>

    @POST(ApiEndPoints.INTERN_CHANGE_PASSWORD)
    @Headers(ApiEndPoints.AUTH, ApiEndPoints.CONTENT, ApiEndPoints.ACCEPT)
    suspend fun changePassword(@Body request: RequestModel): Response<ResponseModel>

    @POST(ApiEndPoints.SEND_OTP)
    @Headers(ApiEndPoints.AUTH, ApiEndPoints.CONTENT)
    suspend fun sendOTP(@Body request: RequestModel): Response<ResponseModel>

    @POST(ApiEndPoints.VERIFY_OTP)
    @Headers(ApiEndPoints.AUTH, ApiEndPoints.CONTENT)
    suspend fun verifyOTP(@Body request: RequestModel): Response<LoginResponseModel>

    @GET(ApiEndPoints.GET_ASSIGNED_TASKS)
    @Headers(ApiEndPoints.AUTH, ApiEndPoints.ACCEPT)
    suspend fun getAssignedTasks(): Response<UserTasksModel>

    @GET(ApiEndPoints.GET_EARNINGS_STRUCTURE)
    @Headers(ApiEndPoints.AUTH, ApiEndPoints.ACCEPT)
    suspend fun getEarningStructure(): Response<EarningStructureModel>

    @GET(ApiEndPoints.GET_EARNING_BY_MONTH)
    @Headers(ApiEndPoints.AUTH, ApiEndPoints.ACCEPT)
    suspend fun getEarningByMonth(@Query("date") date: String): Response<ResponseModel>

    @GET(ApiEndPoints.GET_TOTAL_EARNINGS)
    @Headers(ApiEndPoints.AUTH, ApiEndPoints.ACCEPT)
    suspend fun getTotalEarnings(): Response<ResponseModel>

    @GET(ApiEndPoints.GET_RATINGS)
    @Headers(ApiEndPoints.AUTH, ApiEndPoints.ACCEPT)
    suspend fun getRatings(): Response<RatingModel>

    @POST(ApiEndPoints.ADD_USER_NOTES)
    @Headers(ApiEndPoints.AUTH, ApiEndPoints.CONTENT)
    suspend fun addUserNote(@Body request: RequestModel): Response<ResponseModel>

    @POST(ApiEndPoints.CALL_USER)
    @Headers(ApiEndPoints.AUTH, ApiEndPoints.CONTENT)
    suspend fun callUser(@Body request: RequestModel): Response<ResponseModel>

    @POST(ApiEndPoints.SUBMIT_TASK)
    @Headers(ApiEndPoints.AUTH, ApiEndPoints.CONTENT)
    suspend fun submitTask(@Body request: RequestTaskSubmitModel): Response<ResponseModel>

    @POST(ApiEndPoints.UPDATE_TASK_DUE)
    @Headers(ApiEndPoints.AUTH, ApiEndPoints.CONTENT)
    suspend fun updateTaskDue(@Body request: RequestModel): Response<ResponseModel>

    @POST(ApiEndPoints.UPDATE_TASK_NOT_INTERESTED_REASON)
    @Headers(ApiEndPoints.AUTH, ApiEndPoints.CONTENT)
    suspend fun updateTaskNotInterestedReason(@Body request: RequestModel): Response<ResponseModel>

    @GET(ApiEndPoints.GET_TASK_NOT_INTERESTED_REASONS)
    @Headers(ApiEndPoints.AUTH, ApiEndPoints.ACCEPT)
    suspend fun getTaskNotInterestedReasons(): Response<ReasonsModel>

    @GET(ApiEndPoints.GET_VERSION_CODE)
    @Headers(ApiEndPoints.AUTH, ApiEndPoints.CONTENT, ApiEndPoints.ACCEPT)
    suspend fun getVersionCode(): Response<VersionModel>
}