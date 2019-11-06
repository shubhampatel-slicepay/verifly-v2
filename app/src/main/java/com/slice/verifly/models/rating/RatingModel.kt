package com.slice.verifly.models.rating

import com.google.gson.annotations.SerializedName

data class RatingModel (

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("msg")
    val msg: String? = null,

    @field:SerializedName("avgRating")
    val avgRating: Float? = null,

    @field:SerializedName("totalRating")
    val totalRating: Int? = null,

    @field:SerializedName("totalTasks")
    val totalTasks: Int? = null
)