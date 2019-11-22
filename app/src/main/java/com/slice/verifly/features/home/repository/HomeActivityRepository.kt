package com.slice.verifly.features.home.repository

import com.slice.verifly.base.BaseRepository
import com.slice.verifly.data.repo.DataManagerHelper
import com.slice.verifly.features.home.models.AssignedTasksData
import com.slice.verifly.features.home.models.UsersTasksData
import com.slice.verifly.models.tasks.Task
import com.slice.verifly.models.tasks.TaskDocuments
import com.slice.verifly.utility.AppUtils
import com.slice.verifly.utility.Constants

class HomeActivityRepository(private val dataManagerHelper: DataManagerHelper): BaseRepository() {

    suspend fun getAssignedTasks(): List<Task?>? {
        val obtainedData = dataManagerHelper.getAssignedTasks()
        return if ((AppUtils.isObjectNotEmpty(obtainedData?.body)) and (obtainedData?.body?.status.equals(
                Constants.SUCCESS_RESPONSE_STATUS))) {
            obtainedData?.body?.data
        } else {
            null
        }
    }

    fun formatUsersTasks(usersTasksList: List<Task?>): AssignedTasksData? {
        if (usersTasksList.isNotEmpty()) {
            val newUsersTasksList = mutableListOf<UsersTasksData>()
            val ongoingUsersTasksList = mutableListOf<UsersTasksData>()
            val completedUsersTasksList = mutableListOf<UsersTasksData>()
            for (i in usersTasksList.indices) {
                val userTask = usersTasksList[i]
                if (userTask?.taskDocs?.isNotEmpty() == true) {
                    val newTasksList = mutableListOf<TaskDocuments>()
                    val ongoingTasksList = mutableListOf<TaskDocuments>()
                    val completedTasksList = mutableListOf<TaskDocuments>()
                    for (j in userTask.taskDocs.indices) {
                        val task = userTask.taskDocs[j]
                        when (task?.taskStatus) {
                            Constants.NEW_STATUS -> newTasksList.add(task)
                            Constants.ONGOING_STATUS -> ongoingTasksList.add(task)
                            Constants.COMPLETED_STATUS -> completedTasksList.add(task)
                        }
                    }
                    if (newTasksList.isNotEmpty()) {
                        val tasksOrganizedModel = UsersTasksData(id = userTask.id, taskDocs = newTasksList)
                        newUsersTasksList.add(tasksOrganizedModel)
                        newTasksList.clear()
                    }
                    if (ongoingTasksList.isNotEmpty()) {
                        val tasksOrganizedModel = UsersTasksData(id = userTask.id, taskDocs = ongoingTasksList)
                        ongoingUsersTasksList.add(tasksOrganizedModel)
                        ongoingTasksList.clear()
                    }
                    if (completedTasksList.isNotEmpty()) {
                        val tasksOrganizedModel = UsersTasksData(id = userTask.id, taskDocs = completedTasksList)
                        completedUsersTasksList.add(tasksOrganizedModel)
                        completedTasksList.clear()
                    }
                }
            }
            return AssignedTasksData(
                newTasks = newUsersTasksList,
                ongoingTasks = ongoingUsersTasksList,
                completedTasks = completedUsersTasksList
            )
        } else return null
    }
}