package com.example.ejemplorun

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class], version = 1, exportSchema = true)
abstract class TareasDatabase : RoomDatabase() {
    abstract fun tareaDao(): TareaDao
}
