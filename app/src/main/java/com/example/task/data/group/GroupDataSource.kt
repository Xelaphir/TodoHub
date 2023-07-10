package com.example.task.data.group

import androidx.lifecycle.LiveData
import com.example.task.database.DatabaseBuilder

class GroupDataSource {

    private val database = DatabaseBuilder.get().database
    private val groupDao = database.groupDao()

    fun getGroups(): LiveData<List<Group>> = groupDao.getGroups()

    suspend fun addGroup(group: Group) = groupDao.addGroup(group)

    suspend fun deleteGroup(group: Group) = groupDao.deleteGroup(group)
}