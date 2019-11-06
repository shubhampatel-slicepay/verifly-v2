package com.slice.verifly.models.loginrequest

import com.google.gson.annotations.SerializedName

data class LoginRequestModel (

    @field:SerializedName("userid")
    val userid: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("deviceData")
    val deviceData: DeviceData? = null,

    @field:SerializedName("sessionData")
    val sessionData: SessionData? = null
)