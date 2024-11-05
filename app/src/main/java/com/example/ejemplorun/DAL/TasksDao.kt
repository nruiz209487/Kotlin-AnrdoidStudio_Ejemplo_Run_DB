package com.example.ejemplorun.DAL

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TasksDao {

    @Query("SELECT * FROM task_entity")
    suspend fun getAll(): List<TaskEntity>


    @Query("SELECT * FROM task_entity WHERE id = :id")
    suspend fun get(id: Int): TaskEntity

    @Insert
    suspend fun insert(tarea: TaskEntity)

    @Update
    suspend fun update(tarea: TaskEntity)

    @Delete
    suspend fun delete(tarea: TaskEntity)
}
