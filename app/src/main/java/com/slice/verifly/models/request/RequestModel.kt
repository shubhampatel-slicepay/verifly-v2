package com.slice.verifly.models.request

import com.google.gson.annotations.SerializedName
import java.util.Date

data class RequestModel (

    @field:SerializedName("notes")
    var notes: String? = null,

    @field:SerializedName("userid")
    var userid: String? = null,

    @field:SerializedName("taskID")
    var taskID: String? = null,

    @field:SerializedName("reason")
    var reason: String? = null,

    @field:SerializedName("phone")
    var phone: String? = null,

    @field:SerializedName("otp")
    var otp: String? = null,

    @field:SerializedName("password")
    var password: String? = null,

    @field:SerializedName("scheduleSlot")
    var scheduleSlot: String? = null,

    @field:SerializedName("scheduleDate")
    var scheduleDate: Date? = null
)