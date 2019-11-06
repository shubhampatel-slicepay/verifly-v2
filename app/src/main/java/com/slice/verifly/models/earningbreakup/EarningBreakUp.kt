package com.slice.verifly.models.earningbreakup

import com.google.gson.annotations.SerializedName

data class EarningBreakUp (

    @field:SerializedName("_id")
    val _id: String? = null,

    @field:SerializedName("groupedEarnings")
    val groupedEarnings: Double? = null
)