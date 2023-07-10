package com.example.task.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.example.task.data.task.Task
import com.example.task.data.task.TaskDataSource
import kotlinx.coroutines.launch
import java.util.UUID

class TaskDetailViewModel : ViewModel() {

    private val taskDataSource = TaskDataSource()
    private val id = MutableLiveData<UUID>()
    val task: LiveData<Task?> = id.switchMap { taskId ->
        taskDataSource.getTask(taskId)
    }
    val subtasks: LiveData<List<Task>> = id.switchMap { taskId ->
        taskDataSource.getSubtasks(taskId)
    }

    fun loadTask(id: UUID) {
        this.id.value = id
    }

    fun addSubtask(subtask: Task) = viewModelScope.launch() {
        taskDataSource.add(subtask)
    }

    fun updateTask(task: Task) = viewModelScope.launch() {
        taskDataSource.updateTask(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch() {
        taskDataSource.removeTask(task)
    }

}