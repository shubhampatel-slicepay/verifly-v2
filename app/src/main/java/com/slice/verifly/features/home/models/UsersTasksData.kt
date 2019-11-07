package com.slice.verifly.features.home.models

import android.os.Parcelable
import com.slice.verifly.models.tasks.TaskDocuments
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UsersTasksData(
    var id: String?,
    var taskDocs: List<TaskDocuments>?
) : Parcelable