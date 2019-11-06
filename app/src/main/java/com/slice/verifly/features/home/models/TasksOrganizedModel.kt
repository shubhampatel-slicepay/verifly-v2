package com.slice.verifly.features.home.models

import android.os.Parcelable
import com.slice.verifly.models.tasks.TaskDocuments
import kotlinx.android.parcel.Parcelize

@Parcelize
class TasksOrganizedModel(
    var id: String?,
    var taskDocs: List<TaskDocuments>?
) : Parcelable