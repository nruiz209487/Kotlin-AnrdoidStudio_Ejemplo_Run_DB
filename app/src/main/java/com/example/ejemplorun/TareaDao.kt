package com.example.ejemplorun

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TareaDao {

    @Query("SELECT * FROM task_entity")
    fun getAll(): List<TaskEntity>


    @Query("SELECT * FROM task_entity WHERE id = :id")
    fun get(id: Int): TaskEntity

    @Insert
    fun insert(tarea: TaskEntity)

    @Update
    fun update(tarea: TaskEntity)

    @Delete
    fun delete(tarea: TaskEntity)
}
