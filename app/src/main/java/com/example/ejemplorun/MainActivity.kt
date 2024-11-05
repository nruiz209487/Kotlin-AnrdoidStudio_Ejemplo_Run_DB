package com.example.ejemplorun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.example.ejemplorun.DAL.TaskDatabase
import com.example.ejemplorun.DAL.TaskEntity
import com.example.ejemplorun.ui.theme.EjemploRunTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import com.example.ejemplorun.ui.views.MiAPP
class MainActivity : ComponentActivity() {
    companion object{

        lateinit var database: TaskDatabase
    lateinit var todos: List<TaskEntity>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = Room.databaseBuilder(this, TaskDatabase::class.java, "tareas-db").build()

        runBlocking {
            launch {
                todos = database.tasksDao().getAll()
            }
            val tarea = TaskEntity(name = "tarea 1")
            database.tasksDao().insert(tarea)
        }

        enableEdgeToEdge()

        setContent {
            EjemploRunTheme {
                MiAPP()
            }
        }
    }
}


