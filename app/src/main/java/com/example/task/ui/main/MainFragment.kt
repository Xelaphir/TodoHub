package com.example.task.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.example.task.R
import com.example.task.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(inflater, container, false)

        initCreateFab()

        return binding.root

    }

    private fun initCreateFab() {
        val popupMenu = PopupMenu(requireContext(), binding.createItemFab)
        popupMenu.inflate(R.menu.create_item_menu)

        popupMenu.setOnMenuItemClickListener() {
            when(it.itemId) {
                R.id.create_task -> {
                    Toast.makeText(requireContext(), "Create task", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.create_group -> {
                    Toast.makeText(requireContext(), "Create task", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        binding.createItemFab.setOnClickListener {
            popupMenu.show()
        }

    }

}