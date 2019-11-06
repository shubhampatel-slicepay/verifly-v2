package com.slice.verifly.models.tasks

import com.google.gson.annotations.SerializedName

data class UserTasksModel (

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("currentTime")
    val currentTime: String? = null,

    @field:SerializedName("data")
    val data: List<Task?>? = null
)