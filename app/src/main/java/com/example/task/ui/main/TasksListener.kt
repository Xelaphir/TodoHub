package com.example.task.ui.main

import com.example.task.data.group.Group

interface TasksListener {

    fun getTaskByGroup(group: Group, adapter: TasksAdapter)

}