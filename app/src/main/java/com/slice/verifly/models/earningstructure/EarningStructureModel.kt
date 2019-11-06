package com.slice.verifly.models.earningstructure

import com.google.gson.annotations.SerializedName

data class EarningStructureModel (

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("data")
    val data: ArrayList<EarningStructure?>? = null
)