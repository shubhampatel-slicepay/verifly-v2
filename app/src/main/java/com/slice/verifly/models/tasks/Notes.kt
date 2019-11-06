package com.slice.verifly.models.tasks

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.Date

@Parcelize
data class Notes (

    @field:SerializedName("timestamp")
    val timestamp: Date? = null,

    @field:SerializedName("note")
    val note: String? = null
) : Parcelable