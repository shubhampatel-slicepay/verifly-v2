package com.slice.verifly.models.tasksubmit

import com.google.gson.annotations.SerializedName
import com.slice.verifly.models.tasks.Data

data class RequestTaskSubmitModel (

    @field:SerializedName("userid")
    val userid: String? = null,

    @field:SerializedName("taskID")
    val taskID: String? = null,

    @field:SerializedName("taskStatus")
    val taskStatus: String? = null,

    @field:SerializedName("data")
    val data: Data? = null
)