package com.example.task.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.task.databinding.FragmentTaskDetailBinding
import java.util.UUID

class TaskDetailFragment : Fragment() {

    private lateinit var binding: FragmentTaskDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)

        return binding.root
    }

    companion object {

        private const val ARGS = "args"

        fun newInstance(id: UUID): TaskDetailFragment {
            val arguments = bundleOf(ARGS to id)
            val f = TaskDetailFragment()
            f.arguments = arguments

            return f
        }
    }

}