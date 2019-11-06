package com.slice.verifly.features.home.communicator

import com.slice.verifly.models.tasksorganized.TasksOrganizedModel

interface UsersTasksRecyclerAdapterCallback {

    fun onUserTaskSelected(task: TasksOrganizedModel?)
}