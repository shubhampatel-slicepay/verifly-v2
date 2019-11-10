package com.slice.verifly.features.home.communicator

import com.slice.verifly.models.tasks.TaskDocuments

interface TasksRecyclerAdapterCallback {

    fun onTaskSelected(task: TaskDocuments?)
}