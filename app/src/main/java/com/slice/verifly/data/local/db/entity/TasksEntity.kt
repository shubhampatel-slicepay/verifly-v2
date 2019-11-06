package com.slice.verifly.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.slice.verifly.features.home.models.TasksOrganizedModel

@Entity(tableName = "tasks_table")
class TasksEntity(
    @ColumnInfo(name = "new")
    val newTasks: List<TasksOrganizedModel>,

    @ColumnInfo(name = "ongoing")
    val ongoingTasks: List<TasksOrganizedModel>,

    @ColumnInfo(name = "completed")
    val completedTasks: List<TasksOrganizedModel>
)