package com.example.task.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.task.data.task.Task
import java.util.UUID

@Dao
interface TaskDao {

    @Query("select * from task where isCompleted = 0 and parent is null")
    fun getTasks(): LiveData<List<Task>>

    @Query("select * from task where isFavorite = 1 and isCompleted = 0 and parent is null")
    fun getFavoriteTasks(): LiveData<List<Task>>

    @Query("select * from task where isCompleted = 1 and parent is null")
    fun getCompletedTasks(): LiveData<List<Task>>

    @Query("select * from task where `group`=(:group)")
    fun getTasksByGroup(group: String): LiveData<List<Task>>

    @Query("select * from task where parent=(:parentId)")
    fun getSubtasks(parentId: UUID): LiveData<List<Task>>

    @Query("select * from task where id=(:id)")
    fun getTask(id: UUID): LiveData<Task?>

    @Query("update task set isCompleted = 1 where parent=(:parentId)")
    suspend fun markSubtasksAsCompleted(parentId: UUID)

    @Query("delete from task where parent=(:parentId)")
    suspend fun deleteSubtasks(parentId: UUID)

    @Insert
    suspend fun addTask(task: Task)

    @Update
    suspend fun updateTask(task: Task)

    @Delete
    suspend fun deleteTask(task: Task)
}