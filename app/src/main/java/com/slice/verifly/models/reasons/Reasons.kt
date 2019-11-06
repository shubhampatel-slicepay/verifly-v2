package com.slice.verifly.models.reasons

import com.google.gson.annotations.SerializedName

data class Reasons (

    @field:SerializedName("scheduleDateReason")
    val scheduleDateReason: ArrayList<Reason?>? = null,

    @field:SerializedName("notInterestedReason")
    val notInterestedReason: ArrayList<Reason?>? = null
)