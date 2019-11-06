package com.slice.verifly.models.tasks

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task (

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("totalNew")
    val totalNew: Int? = null,

    @field:SerializedName("totalOngoing")
    val totalOngoing: Int? = null,

    @field:SerializedName("totalCompleted")
    val totalCompleted: Int? = null,

    @field:SerializedName("priorityTasks")
    val priorityTasks: List<PriorityTask?>? = null,

    @field:SerializedName("taskDocs")
    val taskDocs: List<TaskDocuments?>? = null
) : Parcelable