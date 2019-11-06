package com.slice.verifly.features.home.repository

import com.slice.verifly.base.BaseRepository
import com.slice.verifly.data.repo.DataManagerHelper
import com.slice.verifly.models.tasks.Task
import com.slice.verifly.models.tasks.TaskDocuments
import com.slice.verifly.features.home.models.TasksOrganizedModel
import com.slice.verifly.utility.Constants
import com.slice.verifly.utility.isObjectNotEmpty

class DashboardFragmentRepository(private val dataManagerHelper: DataManagerHelper) : BaseRepository() {

    suspend fun getAssignedTasks(): List<Task?>? {
        val obtainedData = dataManagerHelper.getAssignedTasks()
        return if ((obtainedData?.body.isObjectNotEmpty()) and (obtainedData?.body?.status.equals(Constants.SUCCESS_RESPONSE_STATUS))) {
            obtainedData?.body?.data
        } else {
            null
        }
    }

    suspend fun formatUsersTasks(
        usersTasksList: List<Task?>
    ): Triple<MutableList<TasksOrganizedModel>, MutableList<TasksOrganizedModel>, MutableList<TasksOrganizedModel>>? {
        if (usersTasksList.isNotEmpty()) {
            val newUsersTasksList = mutableListOf<TasksOrganizedModel>()
            val ongoingUsersTasksList = mutableListOf<TasksOrganizedModel>()
            val completedUsersTasksList = mutableListOf<TasksOrganizedModel>()
            for (i in usersTasksList.indices) {
                val userTask = usersTasksList[i]
                if (userTask?.taskDocs?.isNotEmpty() == true) {
                    val newTasksList = mutableListOf<TaskDocuments>()
                    val ongoingTasksList = mutableListOf<TaskDocuments>()
                    val completedTasksList = mutableListOf<TaskDocuments>()
                    for (j in userTask.taskDocs.indices) {
                        val task = userTask.taskDocs[j]
                        when (task?.taskStatus) {
                            Constants.NEW_STATUS -> {
                                newTasksList.add(task)
                            }

                            Constants.ONGOING_STATUS -> {
                                ongoingTasksList.add(task)
                            }

                            Constants.COMPLETED_STATUS -> {
                                completedTasksList.add(task)
                            }
                        }
                    }
                    if (newTasksList.isNotEmpty()) {
                        val tasksOrganizedModel = TasksOrganizedModel(id = userTask.id, taskDocs = newTasksList)
                        newUsersTasksList.add(tasksOrganizedModel)
                        newTasksList.clear()
                    }
                    if (ongoingTasksList.isNotEmpty()) {
                        val tasksOrganizedModel = TasksOrganizedModel(id = userTask.id, taskDocs = ongoingTasksList)
                        ongoingUsersTasksList.add(tasksOrganizedModel)
                        ongoingTasksList.clear()
                    }
                    if (completedTasksList.isNotEmpty()) {
                        val tasksOrganizedModel = TasksOrganizedModel(id = userTask.id, taskDocs = completedTasksList)
                        completedUsersTasksList.add(tasksOrganizedModel)
                        completedTasksList.clear()
                    }
                }
            }
            return Triple(newUsersTasksList, ongoingUsersTasksList, completedUsersTasksList)
        } else return null
    }
}