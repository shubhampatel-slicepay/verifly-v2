package com.slice.verifly.data.remote

import com.slice.verifly.BuildConfig

object ApiFactory {

    private const val v2 = "api/v2/"

    fun getBaseUrl() = BuildConfig.SERVER_URL

    fun getV2BaseUrl() = BuildConfig.SERVER_URL.plus(v2)
}