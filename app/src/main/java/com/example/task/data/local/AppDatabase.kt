package com.example.task.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task.model.Task

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): TaskDao
}