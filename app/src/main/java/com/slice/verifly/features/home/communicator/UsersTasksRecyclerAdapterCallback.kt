package com.slice.verifly.features.home.communicator

import com.slice.verifly.features.home.models.UsersTasksData

interface UsersTasksRecyclerAdapterCallback {

    fun onUserTaskSelected(userTask: UsersTasksData?)
}