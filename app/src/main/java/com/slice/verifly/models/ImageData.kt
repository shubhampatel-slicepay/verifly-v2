package com.slice.verifly.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageData (

    @field:SerializedName("imgUrl")
    val imgUrl: String? = null,

    @field:SerializedName("isVerified")
    val isVerified: Boolean? = null
) : Parcelable