package com.slice.verifly.models.reasons

import com.google.gson.annotations.SerializedName

data class ReasonsModel (

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("data")
    val data: Reasons? = null
)