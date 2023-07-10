package com.example.task.ui.main

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.data.group.Group
import com.example.task.data.group.GroupDataSource
import com.example.task.data.task.Task
import com.example.task.data.task.TaskDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val groupDataSource = GroupDataSource()
    private val tasksDataSource = TaskDataSource()
    val groups: LiveData<List<Group>> = groupDataSource.getGroups()
    val tasksByGroup: MutableList<LiveData<List<Task>>> = mutableListOf()

    fun addGroup(group: Group) = viewModelScope.launch(Dispatchers.IO) {
        groupDataSource.addGroup(group)
    }

    fun deleteGroup(group: Group) = viewModelScope.launch(Dispatchers.IO) {
        groupDataSource.deleteGroup(group)
    }

    fun addTask(task: Task) = viewModelScope.launch {
        tasksDataSource.add(task)
    }

    fun getTasksByGroup(group: Group, adapter: TasksAdapter, lifecycleOwner: LifecycleOwner) = viewModelScope.launch() {
        val liveData = tasksDataSource.getTasksByGroup(group.name)
        liveData.observe(lifecycleOwner) {
            adapter.tasks = it
        }
        tasksByGroup.add(liveData)
    }

}