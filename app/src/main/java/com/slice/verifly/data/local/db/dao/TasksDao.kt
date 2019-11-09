package com.slice.verifly.data.local.db.dao

import androidx.room.*

@Dao
interface TasksDao {

    @Query("DELETE FROM tasks_table")
    suspend fun deleteAll()
}