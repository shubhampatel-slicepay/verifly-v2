package com.slice.verifly.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.slice.verifly.data.local.db.dao.TasksDao
import com.slice.verifly.data.local.db.entity.TasksEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//@Database(entities = [TasksEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    // Define all the DAO here

    abstract fun tasksDao(): TasksDao

    companion object {
        private const val DB_NAME = "verifly_database"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun createDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME)
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { database ->
                scope.launch {

                    // Clear all DAO content here.

                    database.tasksDao().deleteAll()
                }
            }
        }
    }
}