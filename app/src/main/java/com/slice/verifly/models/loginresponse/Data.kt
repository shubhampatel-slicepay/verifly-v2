package com.slice.verifly.models.loginresponse

import com.google.gson.annotations.SerializedName

data class Data (

    @field:SerializedName("_id")
    val _id: String? = null,

    @field:SerializedName("userid")
    val userid: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("phone")
    val phone: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("college")
    val college: String? = null,

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("bankAccountNumber")
    val bankAccountNumber: String? = null,

    @field:SerializedName("ifscCode")
    val ifscCode: String? = null,

    @field:SerializedName("payType")
    val payType: String? = null,

    @field:SerializedName("internType")
    val internType: String? = null,

    @field:SerializedName("userToken")
    val userToken: String? = null,

    @field:SerializedName("active")
    val active: Boolean? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)