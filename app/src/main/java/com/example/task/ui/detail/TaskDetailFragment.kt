package com.example.task.ui.detail

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.task.R
import com.example.task.data.group.Group
import com.example.task.data.task.Task
import com.example.task.databinding.CreateTaskDialogBinding
import com.example.task.databinding.FragmentTaskDetailBinding
import com.example.task.ui.main.SubtasksListener
import com.example.task.ui.main.TasksAdapter
import com.example.task.ui.navigator.Navigator
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.UUID

class TaskDetailFragment : Fragment(), SubtasksListener {

    private lateinit var binding: FragmentTaskDetailBinding
    private val viewModel: TaskDetailViewModel by activityViewModels()
    private var navigator: Navigator? = null
    private lateinit var task: Task
    private lateinit var createTaskDialog: BottomSheetDialog
    private val subtaskAdapter = TasksAdapter(this)

    private val titleTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) { task.title = s.toString() }
    }

    private val descriptionTextWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) { task.description = s.toString() }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigator = context as Navigator?
    }

    override fun onDetach() {
        super.onDetach()
        navigator = null
    }

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = requireArguments().getSerializable(ARGS) as UUID
        viewModel.loadTask(id)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)

        initCreateTaskDialog()
        initSubtaskRecyclerView()

        binding.goBackBtn.setOnClickListener {
            viewModel.updateTask(task)
            navigator?.goBack()
        }

        binding.addToFavoriteCheckBox.setOnCheckedChangeListener { btn, isChecked ->
            task.isFavorite = isChecked
            btn.setButtonDrawable(if (isChecked) R.drawable.icon_star else R.drawable.icon_star_border)
        }

        binding.deleteBtn.setOnClickListener {
            viewModel.deleteTask(task)
            navigator?.goBack()
        }

        binding.addToCompletedButton.setOnClickListener {
            task.isCompleted = !task.isCompleted
            if (task.isCompleted) {
                viewModel.updateTask(task)
                navigator?.goBack()
            } else {
                updateScreen()
            }
        }

        binding.addSubtaskImageButton.setOnClickListener {
            createTaskDialog.show()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.task.observe(viewLifecycleOwner) { task ->
            task?.let {
                this.task = it
                updateScreen()
            }
        }

        viewModel.subtasks.observe(viewLifecycleOwner) {
            subtaskAdapter.tasks = it
        }
    }

    override fun onStart() {
        super.onStart()
        binding.titleEditText.addTextChangedListener(titleTextWatcher)
        binding.descriptionText.addTextChangedListener(descriptionTextWatcher)
    }

    override fun onStop() {
        super.onStop()
        binding.titleEditText.removeTextChangedListener(titleTextWatcher)
        binding.descriptionText.removeTextChangedListener(descriptionTextWatcher)
    }

    private fun initSubtaskRecyclerView() {
        binding.subtasksRecyclerView.adapter = subtaskAdapter
        binding.subtasksRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun updateScreen() = binding.run {
        titleEditText.setText(task.title)
        descriptionText.setText(task.description)
        addToFavoriteCheckBox.apply {
            isChecked = task.isFavorite
            setButtonDrawable(if (isChecked) R.drawable.icon_star else R.drawable.icon_star_border)
        }
        addToCompletedButton.setText(if (task.isCompleted) R.string.uncompleted else R.string.completed)
    }

    private fun initCreateTaskDialog() {

        createTaskDialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
        createTaskDialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        val dialogBinding = CreateTaskDialogBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        dialogBinding.saveTaskButton.isEnabled = false

        dialogBinding.descriptionImageButton.setOnClickListener {
            dialogBinding.descriptionEditText.visibility = View.VISIBLE
        }
        dialogBinding.addToFavoriteCheckBox.setOnCheckedChangeListener { _, isChecked ->
            dialogBinding.addToFavoriteCheckBox.setButtonDrawable(
                if (isChecked) R.drawable.icon_star else R.drawable.icon_star_border
            )
        }

        dialogBinding.titleEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                dialogBinding.saveTaskButton.isEnabled = s?.isNotBlank() == true
            }
        })

        dialogBinding.saveTaskButton.setOnClickListener {

            val task = Task(
                title = dialogBinding.titleEditText.text.toString(),
                description = dialogBinding.descriptionEditText.text.toString(),
                isFavorite = dialogBinding.addToFavoriteCheckBox.isChecked,
                isCompleted = false,
                parent = null,
                group = ""
            )
            viewModel.addSubtask(task)

            createTaskDialog.dismiss()
        }
        dialogBinding.titleEditText.requestFocus()

        createTaskDialog.setOnDismissListener {
            dialogBinding.apply {
                titleEditText.text.clear()
                descriptionEditText.text.clear()
                descriptionEditText.visibility = View.GONE
                addToFavoriteCheckBox.isChecked = false
            }
        }

        createTaskDialog.setContentView(dialogBinding.root)
    }

    override fun deleteTask(task: Task) {
        viewModel.deleteTask(task)
    }

    override fun updateTask(task: Task) {
        viewModel.updateTask(task)
    }

    override fun getTaskByGroup(group: Group, adapter: TasksAdapter) {}

    override fun taskItemPressed(id: UUID) {}

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