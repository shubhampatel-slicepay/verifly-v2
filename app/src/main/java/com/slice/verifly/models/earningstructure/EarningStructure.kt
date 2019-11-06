package com.slice.verifly.models.earningstructure

import com.google.gson.annotations.SerializedName

data class EarningStructure (

    @field:SerializedName("task")
    val task: String? = null,

    @field:SerializedName("minimumEarnings")
    val minimumEarnings: String? = null,

    @field:SerializedName("potentialEarnings")
    val potentialEarnings: String? = null
)