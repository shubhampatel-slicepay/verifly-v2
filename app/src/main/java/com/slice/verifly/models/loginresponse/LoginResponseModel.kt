package com.slice.verifly.models.loginresponse

import com.google.gson.annotations.SerializedName

data class LoginResponseModel (

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("userToken")
    val userToken: String? = null,

    @field:SerializedName("data")
    val data: Data? = null
)