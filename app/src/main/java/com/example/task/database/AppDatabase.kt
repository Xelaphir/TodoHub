package com.example.task.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task.data.group.Group
import com.example.task.data.task.Task

@Database(entities = [Group::class, Task::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun groupDao(): GroupDao
    abstract fun taskDao(): TaskDao
}