package com.example.ejemplorun.ui.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import com.example.ejemplorun.DAL.TaskEntity
import com.example.ejemplorun.MainActivity.Companion.database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

// Composable para mostrar la lista de tareas
@Composable
fun Lista(listaTareas: List<TaskEntity>, coroutineScope: CoroutineScope) {
    // LazyColumn permite una lista desplazable que solo carga los elementos visibles
    LazyColumn {
        // Itera sobre la lista de tareas y muestra cada tarea en una fila
        items(listaTareas) { task -> FilaTarea(task, coroutineScope) }
    }
}

/**
 * Composable para crear una nueva tarea
 */
@Composable
fun NuevaTarea(listaTareas: MutableList<TaskEntity>, coroutineScope: CoroutineScope) {
    // Estado para almacenar el texto de la nueva tarea
    var text by remember { mutableStateOf("") }

    // Fila para alinear el campo de texto y el botón
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Campo de texto para ingresar la nueva tarea
        TextField(
            value = text,
            onValueChange = { text = it }, // Actualiza el texto al cambiar
            label = { Text("Nueva tarea") }, // Etiqueta para el campo de texto
        )

        // Botón para agregar la nueva tarea
        Button(onClick = {
            // Crea una nueva tarea con el texto ingresado
            var tarea = TaskEntity()
            tarea.name = text
            // Inserta la tarea en la base de datos y la agrega a la lista
            coroutineScope.launch {
                database.tasksDao().insert(tarea)
                listaTareas.add(tarea) // Agrega la tarea a la lista
            }
        }) {
            Text(text = "Agregar") // Texto del botón
        }
    }
}

/**
 * Composable para mostrar una fila de tarea
 */
@Composable
fun FilaTarea(tarea: TaskEntity, coroutineScope: CoroutineScope) {
    // Estado para manejar el estado de la tarea (completada o no)
    var checked by remember { mutableStateOf(tarea.isDone) }
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
        // Checkbox para marcar la tarea como completada
        Checkbox(checked = checked, onCheckedChange = { isChecked ->
            checked = isChecked // Actualiza el estado de la tarea
            coroutineScope.launch {
                tarea.isDone = isChecked // Actualiza el estado en la entidad
                database.tasksDao().update(tarea) // Actualiza la tarea en la base de datos
            }
        })
        Text(text = tarea.name) // Muestra el nombre de la tarea
    }
}

// Composable principal de la aplicación
@Composable
fun MiAPP() {
    val coroutineScope = rememberCoroutineScope() // Crea un scope para las corutinas
    val listaTareas = remember { mutableStateListOf<TaskEntity>() } // Lista mutable de tareas

    // Efecto lanzado que se ejecuta al iniciar la composición
    LaunchedEffect(Unit) {
        listaTareas.clear() // Limpia la lista
        listaTareas.addAll(database.tasksDao().getAll()) // Carga todas las tareas de la base de datos
    }
    // Columna para organizar los elementos verticalmente
    Column {
        NuevaTarea(listaTareas, coroutineScope) // Componente para agregar una nueva tarea
        Lista(listaTareas, coroutineScope) // Componente para mostrar la lista de tareas
    }
}
