package com.slice.verifly.features.home.communicator

import com.slice.verifly.features.home.models.TasksOrganizedModel

interface UsersTasksRecyclerAdapterCallback {

    fun onUserTaskSelected(task: TasksOrganizedModel?)
}