package com.example.task.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task.data.group.Group

@Database(entities = [Group::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun groupDao(): GroupDao
}