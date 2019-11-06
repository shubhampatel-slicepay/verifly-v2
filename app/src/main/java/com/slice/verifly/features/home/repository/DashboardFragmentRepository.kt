package com.slice.verifly.features.home.repository

import com.slice.verifly.base.BaseRepository
import com.slice.verifly.data.repo.DataManagerHelper
import com.slice.verifly.models.tasks.Task
import com.slice.verifly.models.tasks.TaskDocuments
import com.slice.verifly.models.tasksorganized.TasksOrganizedModel
import com.slice.verifly.utility.Constants
import com.slice.verifly.utility.isObjectNotEmpty

class DashboardFragmentRepository(private val dataManagerHelper: DataManagerHelper): BaseRepository() {

    var newUsersTasksList: MutableList<TasksOrganizedModel>? = null
    var ongoingUsersTasksList: MutableList<TasksOrganizedModel>? = null
    var completedUsersTasksList: MutableList<TasksOrganizedModel>? = null

    suspend fun getAssignedTasks(): List<Task?>? {
        val obtainedData = dataManagerHelper.getAssignedTasks()
        return if ((obtainedData?.body.isObjectNotEmpty()) and (obtainedData?.body?.status.equals(Constants.SUCCESS_RESPONSE_STATUS))) {
            obtainedData?.body?.data?.let {
                formatUsersTasks(it)
            }
            obtainedData?.body?.data
        } else {
            null
        }
    }

    private fun formatUsersTasks(usersTasksList: List<Task?>) {
        if (usersTasksList.isNotEmpty()) {
            val usersTasksIterator = usersTasksList.iterator()
            newUsersTasksList = mutableListOf()
            ongoingUsersTasksList = mutableListOf()
            completedUsersTasksList = mutableListOf()
            while (usersTasksIterator.hasNext() and usersTasksIterator.next().isObjectNotEmpty()) {
                val userTask = usersTasksIterator.next()
                if (userTask?.taskDocs?.isNotEmpty() == true) {
                    val tasksIterator = userTask.taskDocs.iterator()
                    val newTasksList: MutableList<TaskDocuments> = mutableListOf()
                    val ongoingTasksList: MutableList<TaskDocuments> = mutableListOf()
                    val completedTasksList: MutableList<TaskDocuments> = mutableListOf()
                    while (tasksIterator.hasNext() and tasksIterator.next().isObjectNotEmpty()) {
                        val task = tasksIterator.next()
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
                        newUsersTasksList?.add(TasksOrganizedModel(userTask.id, newTasksList))
                        newTasksList.clear()
                    }
                    if (ongoingTasksList.isNotEmpty()) {
                        ongoingUsersTasksList?.add(TasksOrganizedModel(userTask.id, ongoingTasksList))
                        ongoingTasksList.clear()
                    }
                    if (completedTasksList.isNotEmpty()) {
                        completedUsersTasksList?.add(TasksOrganizedModel(userTask.id, completedTasksList))
                        completedTasksList.clear()
                    }
                }
            }
        }
    }
}