package com.slice.verifly.features.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.slice.verifly.base.BaseViewModel
import com.slice.verifly.base.ErrorCommunicator
import com.slice.verifly.features.home.repository.DashboardFragmentRepository
import com.slice.verifly.models.tasks.Task
import com.slice.verifly.features.home.models.TasksOrganizedModel
import com.slice.verifly.utility.Constants
import kotlinx.coroutines.Dispatchers

class DashboardFragmentViewModel(private val repository: DashboardFragmentRepository) : BaseViewModel(), ErrorCommunicator {

    var newUsersTasksList: List<TasksOrganizedModel>? = null
    var ongoingUsersTasksList: List<TasksOrganizedModel>? = null
    var completedUsersTasksList: List<TasksOrganizedModel>? = null

    fun getAssignedTasks(): LiveData<List<Task?>?> {
        return liveData(Dispatchers.IO) {
            emit(repository.getAssignedTasks())
        }
    }

    fun formatUsersTasks(
        data: List<Task?>
    ): LiveData<Triple<MutableList<TasksOrganizedModel>, MutableList<TasksOrganizedModel>, MutableList<TasksOrganizedModel>>?> {
        return liveData(Dispatchers.IO) {
            val formattedUsersTasks
                    = repository.formatUsersTasks(data)
            save(formattedUsersTasks)
            emit(formattedUsersTasks)
        }
    }

    private fun save(
        formattedUsersTasks: Triple<MutableList<TasksOrganizedModel>, MutableList<TasksOrganizedModel>, MutableList<TasksOrganizedModel>>?
    ) {
        formattedUsersTasks?.let {
            newUsersTasksList = listOf()
            newUsersTasksList = it.first
            ongoingUsersTasksList = listOf()
            ongoingUsersTasksList = it.second
            completedUsersTasksList = listOf()
            completedUsersTasksList = it.third
        }
    }

    override fun notifyOnError(errorMessage: String, nullify: Boolean?) {
        if (nullify == true) {
            errorLiveData.value = null
        }
        errorLiveData.value = errorMessage
    }

    fun noInternet() {
        noInternetLiveData.value = null
        noInternetLiveData.value = Constants.NO_INTERNET_MESSAGE
    }
}