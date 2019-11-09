package com.slice.verifly.data.local.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.slice.verifly.features.home.models.UsersTasksData

@Entity(tableName = "tasks_table")
class TasksEntity(

    @PrimaryKey(autoGenerate = true)
    var slNo: Int
)