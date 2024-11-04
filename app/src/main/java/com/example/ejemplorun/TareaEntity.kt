package com.example.ejemplorun

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_entity")
data class TaskEntity (
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0, // Id de la tarea
    var name:String = "", // Nombre de la tarea
    var isDone:Boolean = false // Booleano que indica si la tarea está hecha o no
)
