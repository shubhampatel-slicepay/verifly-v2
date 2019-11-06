package com.slice.verifly.models.response

import com.google.gson.annotations.SerializedName
import com.slice.verifly.models.earningbreakup.EarningBreakUp
import java.util.Date

data class ResponseModel (

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("token")
    val token: String? = null,

    @field:SerializedName("expiresAt")
    val expiresAt: Long? = null,

    @field:SerializedName("earnings")
    val earnings: Double? = null,

    @field:SerializedName("earningBreakUp")
    val earningBreakUp: ArrayList<EarningBreakUp?>? = null,

    @field:SerializedName("scheduledData")
    val scheduledData: Date? = null
)