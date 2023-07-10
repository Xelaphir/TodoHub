package com.example.task.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.databinding.ItemTaskBinding

class TasksAdapter : RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    class TasksViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemTaskBinding.bind(view)

        fun bind(task: String) {
            binding.titleTextView.text = task
        }
    }

    var tasks: List<String> = listOf()
        set(value) {
            if (value == field) return
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        return TasksViewHolder(view)
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
       holder.bind(tasks[position])
    }
}