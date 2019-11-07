package com.slice.verifly.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.slice.verifly.features.home.models.UsersTasksData

@Entity(tableName = "tasks_table")
class TasksEntity(
    @ColumnInfo(name = "new")
    val newTasks: List<UsersTasksData>,

    @ColumnInfo(name = "ongoing")
    val ongoingTasks: List<UsersTasksData>,

    @ColumnInfo(name = "completed")
    val completedTasks: List<UsersTasksData>
)