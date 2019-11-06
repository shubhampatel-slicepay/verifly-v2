package com.slice.verifly.models.tasksorganized

import android.os.Parcelable
import com.slice.verifly.models.tasks.TaskDocuments
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TasksOrganizedModel(
    var id: String? = null,
    var taskDocs: List<TaskDocuments>? = null
) : Parcelable