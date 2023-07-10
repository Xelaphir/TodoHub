package com.example.task.ui.main

import com.example.task.data.group.Group
import com.example.task.data.task.Task
import java.util.UUID

interface TasksListener {

    fun getTaskByGroup(group: Group, adapter: TasksAdapter)

    fun updateTask(task: Task)

    fun taskItemPressed(id: UUID)

}