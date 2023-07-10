package com.example.task.data.group

import androidx.room.Entity
import androidx.room.PrimaryKey

const val FAVORITE = "Favorite"
const val ALL_TASKS = "All tasks"
const val COMPLETED = "Completed"
val BASE_GROUPS = listOf<String>(
    FAVORITE,
    ALL_TASKS,
    COMPLETED
)
@Entity
data class Group(
    @PrimaryKey val name: String,
)