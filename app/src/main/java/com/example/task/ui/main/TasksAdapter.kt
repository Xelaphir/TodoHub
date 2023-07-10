package com.example.task.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.data.task.Task
import com.example.task.databinding.ItemTaskBinding

class TasksAdapter(private val listener: TasksListener) : RecyclerView.Adapter<TasksAdapter.TasksViewHolder>() {

    class TasksViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemTaskBinding.bind(view)

        fun bind(task: Task, listener: TasksListener) = binding.run {
            titleTextView.text = task.title

            if (task.description.isNotBlank()) {
                descriptionTextView.visibility = View.VISIBLE
                descriptionTextView.text = task.description
            } else {
                descriptionTextView.visibility = View.GONE
            }

            isCompletedCheckBox.isChecked = task.isCompleted

            if (listener !is SubtasksListener) {
                isFavoriteImageButton.visibility = View.VISIBLE
                val imageRes = if (task.isFavorite) R.drawable.icon_star else R.drawable.icon_star_border
                isFavoriteImageButton.setImageResource(imageRes)

                deleteImageButton.visibility = View.GONE
            } else {
                isFavoriteImageButton.visibility = View.GONE

                deleteImageButton.visibility = View.VISIBLE
                deleteImageButton.setOnClickListener { listener.deleteTask(task) }
            }

            root.setOnClickListener {
                listener.taskItemPressed(task.id)
            }

            isCompletedCheckBox.setOnClickListener {
                task.isCompleted = isCompletedCheckBox.isChecked
                listener.updateTask(task)
            }

            isFavoriteImageButton.setOnClickListener {
                task.isFavorite = !task.isFavorite
                val image = if (task.isFavorite) R.drawable.icon_star else R.drawable.icon_star_border
                isFavoriteImageButton.setImageResource(image)
                listener.updateTask(task)
            }
        }
    }

    var tasks: List<Task> = listOf()
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
       holder.bind(tasks[position], listener)
    }
}