package com.slice.verifly.features.home.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.slice.verifly.base.BaseViewModel
import com.slice.verifly.base.ErrorCommunicator
import com.slice.verifly.models.tasks.Task
import com.slice.verifly.models.tasksorganized.TasksOrganizedModel
import com.slice.verifly.utility.Constants
import kotlinx.coroutines.Dispatchers

class DashboardFragmentViewModel(private val repository: DashboardFragmentRepository): BaseViewModel(), ErrorCommunicator {

    fun getAssignedTasks(): LiveData<List<Task?>?> {
        return liveData(Dispatchers.IO) {
            emit(repository.getAssignedTasks())
        }
    }

    fun getNewUsersTasks(): LiveData<List<TasksOrganizedModel>?> {
        return liveData(Dispatchers.Default) {
            emit(repository.newUsersTasksList?.toList())
        }
    }

    fun getOngoingUsersTasks(): LiveData<List<TasksOrganizedModel>?> {
        return liveData(Dispatchers.Default) {
            emit(repository.ongoingUsersTasksList?.toList())
        }
    }

    fun getCompletedUsersTasks(): LiveData<List<TasksOrganizedModel>?> {
        return liveData(Dispatchers.Default) {
            emit(repository.completedUsersTasksList?.toList())
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