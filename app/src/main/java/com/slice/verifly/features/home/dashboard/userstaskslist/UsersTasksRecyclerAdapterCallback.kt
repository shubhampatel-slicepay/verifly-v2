package com.slice.verifly.features.home.dashboard.userstaskslist

import com.slice.verifly.models.tasksorganized.TasksOrganizedModel

interface UsersTasksRecyclerAdapterCallback {

    fun onUserTaskSelected(task: TasksOrganizedModel?)
}