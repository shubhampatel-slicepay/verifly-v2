package com.slice.verifly.data.remote.interceptors

import com.slice.verifly.BuildConfig
import com.slice.verifly.data.repo.Decisions
import com.slice.verifly.utility.Constants
import com.slice.verifly.utility.SlicePayLog
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

object AuthInterceptor : Interceptor {

    private val TAG = "AuthInterceptor"

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val request = addHeader(originalRequest)
        val response = chain.proceed(request)
        return when (response.code()) {
            401, 403 -> handleUnAuthorizedRequest(chain, originalRequest, response)
            else -> response
        }
    }

    private fun addHeader(originalRequest: Request): Request {
        return originalRequest.newBuilder().apply {
            addHeader(Constants.X_ACCESS_TOKEN_AUTH_HEADER, Decisions.Manager.fetchXAccessToken())
            addHeader(Constants.I_ACCESS_TOKEN_AUTH_HEADER, Decisions.Manager.fetchUToken())
            addHeader(Constants.PLATFORM_AUTH_HEADER, Constants.ANDROID.plus(":").plus(BuildConfig.VERSION_CODE))
            method(originalRequest.method(), originalRequest.body())
            cacheControl(CacheControl.FORCE_NETWORK)
        }.build()
    }

    private fun handleUnAuthorizedRequest(
        chain: Interceptor.Chain,
        originalRequest: Request,
        response: Response
    ): Response {
        if ((response.body() != null) and (Decisions.Manager.fetchUToken().isNotEmpty())) {
            Decisions.Manager.logOutUser(isTokenExpired = true)
            return response
        } else {
            return reRequest(chain, originalRequest, response)
        }
    }

    private fun reRequest(
        chain: Interceptor.Chain,
        originalRequest: Request,
        response: Response
    ): Response {
        try {
            if (Decisions.Manager.generateToken()) {
                val request = addHeader(originalRequest)
                return chain.proceed(request)
            }
        } catch (e: Exception) {
            SlicePayLog.info(TAG, e.message)
            SlicePayLog.info(TAG, e.stackTrace.toString())
        }
        return response
    }
}