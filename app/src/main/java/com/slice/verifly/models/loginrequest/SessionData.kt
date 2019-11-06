package com.slice.verifly.models.loginrequest

import com.google.gson.annotations.SerializedName

data class SessionData (

    @field:SerializedName("client")
    val client: String? = null,

    @field:SerializedName("timestamp")
    val timestamp: String? = null
)