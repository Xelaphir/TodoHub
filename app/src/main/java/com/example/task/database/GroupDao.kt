package com.example.task.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.task.data.group.Group

@Dao
interface GroupDao {

    @Query("select * from `group`")
    fun getGroups(): LiveData<List<Group>>

    @Insert
    suspend fun addGroup(group: Group)

    @Delete
    suspend fun deleteGroup(group: Group)

}