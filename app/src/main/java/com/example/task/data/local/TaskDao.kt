package com.example.task.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.task.model.Task

@Dao
interface TaskDao {
    @Query("SELECT * FROM task")
    fun getAll(): List<Task>

    @Insert
    fun insert(task: Task)

    @Delete
    fun delete(task: Task)
}