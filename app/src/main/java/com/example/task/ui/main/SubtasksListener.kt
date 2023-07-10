package com.example.task.ui.main

import com.example.task.data.task.Task

interface SubtasksListener : TasksListener {

    fun deleteTask(task: Task)

}