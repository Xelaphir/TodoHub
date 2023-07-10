package com.example.task.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.data.group.Group
import com.example.task.data.group.GroupDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val groupDataSource = GroupDataSource()
    val groups: LiveData<List<Group>> = groupDataSource.getGroups()

    fun addGroup(group: Group) = viewModelScope.launch(Dispatchers.IO) {
        groupDataSource.addGroup(group)
    }

    fun deleteGroup(group: Group) = viewModelScope.launch(Dispatchers.IO) {
        groupDataSource.deleteGroup(group)
    }

}