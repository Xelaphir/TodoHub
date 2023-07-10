package com.example.task.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.data.group.Group
import com.example.task.databinding.ItemGroupBinding

class GroupAdapter(private val listener: TasksListener) : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    class GroupViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemGroupBinding.bind(view)
        private val adapter = TasksAdapter()

        fun bind(group: Group, listener: TasksListener) {
            val layoutManager = LinearLayoutManager(binding.root.context)
            binding.groupRecyclerView.layoutManager = layoutManager
            binding.groupRecyclerView.adapter = adapter

            listener.getTaskByGroup(group, adapter)
        }
    }

     var groups: MutableList<Group> = mutableListOf()
        set(value) {
            if (value == field) return
            field = value
            notifyDataSetChanged()
        }

    fun addGroup(group: Group) {
        groups.add(group)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        return GroupViewHolder(view)
    }

    override fun getItemCount(): Int = groups.size

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(groups[position], listener)
    }
}