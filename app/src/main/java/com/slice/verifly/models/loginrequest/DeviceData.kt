package com.slice.verifly.models.loginrequest

import com.google.gson.annotations.SerializedName

data class DeviceData (

    @field:SerializedName("deviceID")
    val deviceID: String? = null,

    @field:SerializedName("SIM")
    val SIM: String? = null,

    @field:SerializedName("connectionType")
    val connectionType: String? = null,

    @field:SerializedName("deviceCarrier")
    val deviceCarrier: String? = null,

    @field:SerializedName("osVersion")
    val osVersion: String? = null,

    @field:SerializedName("deviceModel")
    val deviceModel: String? = null,

    @field:SerializedName("userAgent")
    val userAgent: String? = null,

    @field:SerializedName("appVersion")
    val appVersion: String? = null
)