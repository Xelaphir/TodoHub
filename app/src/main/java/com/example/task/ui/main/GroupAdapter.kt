package com.example.task.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.databinding.ItemGroupBinding

class GroupAdapter : RecyclerView.Adapter<GroupAdapter.GroupViewHolder>() {

    class GroupViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = ItemGroupBinding.bind(view)
        private val adapter = TasksAdapter()

        fun bind(group: String) {
            val layoutManager = LinearLayoutManager(binding.root.context)
            binding.groupRecyclerView.layoutManager = layoutManager
            binding.groupRecyclerView.adapter = adapter
            //load tasks by group
        }
    }

     var groups: MutableList<String> = mutableListOf()
        set(value) {
            if (value == field) return
            field = value
            notifyDataSetChanged()
        }

    fun addGroup(group: String) {
        groups.add(group)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_group, parent, false)
        return GroupViewHolder(view)
    }

    override fun getItemCount(): Int = groups.size

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.bind(groups[position])
    }
}