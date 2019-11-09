package com.slice.verifly.features.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.slice.verifly.base.BaseViewModel
import com.slice.verifly.base.ErrorCommunicator
import com.slice.verifly.features.home.models.AssignedTasksData
import com.slice.verifly.features.home.repository.HomeActivityRepository
import com.slice.verifly.models.tasks.Task
import com.slice.verifly.utility.Constants
import kotlinx.coroutines.Dispatchers

class HomeActivityViewModel(private val repository: HomeActivityRepository): BaseViewModel(), ErrorCommunicator {

    // Properties

    var assignedTasksData: AssignedTasksData? = null

    // Business logic

    fun getAssignedTasks(): LiveData<List<Task?>?> {
        return liveData(Dispatchers.IO) {
            emit(repository.getAssignedTasks())
        }
    }

    fun formatUsersTasks(data: List<Task?>): LiveData<AssignedTasksData?> {
        return liveData(Dispatchers.IO) {
            assignedTasksData = repository.formatUsersTasks(data)
            emit(assignedTasksData)
        }
    }
}