package com.example.task.data.task

import androidx.lifecycle.LiveData
import androidx.room.Database
import com.example.task.database.DatabaseBuilder
import java.util.UUID

class TaskDataSource {

    private val database = DatabaseBuilder.get().database
    private val tasksDao = database.taskDao()

    fun getTask(id: UUID): LiveData<Task?> = tasksDao.getTask(id)
    fun getTasks(): LiveData<List<Task>> = tasksDao.getTasks()
    fun getCompletedTasks(): LiveData<List<Task>> = tasksDao.getCompletedTasks()
    fun getFavouriteTasks(): LiveData<List<Task>> = tasksDao.getFavoriteTasks()
    fun getTasksByGroup(group: String): LiveData<List<Task>> = tasksDao.getTasksByGroup(group)
    fun getSubtasks(parentId: UUID): LiveData<List<Task>> = tasksDao.getSubtasks(parentId)

    suspend fun updateTask(task: Task) {

        tasksDao.updateTask(task)
        if (task.isCompleted) {
            tasksDao.markSubtasksAsCompleted(task.id)
        }
    }

    suspend fun removeTask(task: Task) {

        tasksDao.deleteTask(task)
        tasksDao.deleteSubtasks(task.id)
    }

    suspend fun add(task: Task) {

        tasksDao.addTask(task)
    }

}