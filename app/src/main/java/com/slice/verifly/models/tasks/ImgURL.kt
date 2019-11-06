package com.slice.verifly.models.tasks

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImgURL (

    @field:SerializedName("imgUrl")
    val imgUrl: String? = null
) : Parcelable