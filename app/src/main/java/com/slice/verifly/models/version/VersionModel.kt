package com.slice.verifly.models.version

import com.google.gson.annotations.SerializedName

data class VersionModel (

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("data")
    val data: Data? = null
)