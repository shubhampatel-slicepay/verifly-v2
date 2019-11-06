package com.slice.verifly.models.tasks

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserAddress (

    @field:SerializedName("line1")
    val line1: String? = null,

    @field:SerializedName("line2")
    val line2: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("pincode")
    val pincode: String? = null
) : Parcelable