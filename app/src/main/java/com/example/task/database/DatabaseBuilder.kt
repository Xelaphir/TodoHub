package com.example.task.database

import android.content.Context
import androidx.room.Room

private const val DATABASE_NAME = "tasks-database"

class DatabaseBuilder private constructor(context: Context) {

    val database: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        DATABASE_NAME
    ).build()


    companion object {

        private var INSTANCE: DatabaseBuilder? = null

        fun get(): DatabaseBuilder {
            return INSTANCE ?: throw IllegalStateException("Database must be initialize")
        }

        fun build(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = DatabaseBuilder(context)
            }
        }

    }
}