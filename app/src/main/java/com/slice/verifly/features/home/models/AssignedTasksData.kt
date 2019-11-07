package com.slice.verifly.features.home.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AssignedTasksData (
    var newTasks: List<UsersTasksData>?,
    var ongoingTasks: List<UsersTasksData>?,
    var completedTasks: List<UsersTasksData>?
) : Parcelable