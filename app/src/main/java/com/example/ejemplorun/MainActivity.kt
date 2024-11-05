package com.example.ejemplorun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.ejemplorun.DAL.TaskDatabase
import com.example.ejemplorun.DAL.TasksDao
import com.example.ejemplorun.DAL.TaskEntity
import com.example.ejemplorun.ui.theme.EjemploRunTheme
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    companion object{
    lateinit var basedatos: TaskDatabase
    lateinit var todos: List<TaskEntity>
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        basedatos = Room.databaseBuilder(this, TaskDatabase::class.java, "tareas-db").build()

        runBlocking {
            launch {
                todos = basedatos.tasksDao().getAll()
            }
            val tarea = TaskEntity(name = "tarea 1")
            basedatos.tasksDao().insert(tarea)
        }

        enableEdgeToEdge()

        setContent {
            EjemploRunTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android", modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(text = "Hello $name!")
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    EjemploRunTheme {
        Greeting("Android")
    }
}
