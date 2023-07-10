package com.example.task

import android.app.Application
import com.example.task.database.DatabaseBuilder

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DatabaseBuilder.build(this)
    }

}