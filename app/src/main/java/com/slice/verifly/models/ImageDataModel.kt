package com.slice.verifly.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageDataModel (

    @field:SerializedName("validImgUrls")
    val validImgUrls: List<String?>? = null,

    @field:SerializedName("invalidImgUrls")
    val invalidImgUrls: List<String?>? = null,

    @field:SerializedName("imgUrls")
    val imgUrls: List<String?>? = null,

    @field:SerializedName("newImgUrls")
    val newImgUrls: Map<String?, String?>? = null,

    @field:SerializedName("isUpdateNewImgUrls")
    val isUpdateNewImgUrls: Boolean? = null,

    @field:SerializedName("back")
    val back: ImageData? = null,

    @field:SerializedName("isUpdateFront")
    val isUpdateFront: Boolean? = null,

    @field:SerializedName("frontStatus")
    val frontStatus: String? = null,

    @field:SerializedName("front")
    val front: ImageData? = null,

    @field:SerializedName("isUpdateBack")
    val isUpdateBack: Boolean? = null,

    @field:SerializedName("backStatus")
    val backStatus: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("totalImageSize")
    val totalImageSize: Int? = null
) : Parcelable