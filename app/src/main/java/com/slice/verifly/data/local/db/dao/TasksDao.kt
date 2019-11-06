package com.slice.verifly.data.local.db.dao

import androidx.room.*
import com.slice.verifly.data.local.db.entity.TasksEntity
import com.slice.verifly.features.home.models.TasksOrganizedModel

@Dao
interface TasksDao {

    @Query("SELECT new from tasks_table")
    suspend fun fetchNewTasks(): List<TasksOrganizedModel>

    @Query("SELECT ongoing from tasks_table")
    suspend fun fetchOngoingTasks(): List<TasksOrganizedModel>

    @Query("SELECT completed from tasks_table")
    suspend fun fetchCompletedTasks(): List<TasksOrganizedModel>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(tasksEntity: TasksEntity)

    @Query("DELETE FROM tasks_table")
    suspend fun deleteAll()
}