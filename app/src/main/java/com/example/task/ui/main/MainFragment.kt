package com.example.task.ui.main

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.task.R
import com.example.task.data.group.BASE_GROUPS
import com.example.task.data.group.Group
import com.example.task.data.task.Task
import com.example.task.databinding.CreateGroupDialogBinding
import com.example.task.databinding.CreateTaskDialogBinding
import com.example.task.databinding.FragmentMainBinding
import com.example.task.ui.detail.TaskDetailFragment
import com.example.task.ui.navigator.Navigator
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import java.util.UUID

class MainFragment : Fragment(), TasksListener {

    private lateinit var binding: FragmentMainBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var navigator: Navigator? = null
    private val groupAdapter: GroupAdapter = GroupAdapter(this)
    private lateinit var createTaskDialog: BottomSheetDialog
    private lateinit var createGroupDialog: BottomSheetDialog

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigator = context as Navigator
    }

    override fun onDetach() {
        super.onDetach()
        navigator = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BASE_GROUPS.forEach {
            viewModel.addGroup(Group(it))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMainBinding.inflate(inflater, container, false)

        initViewPager()
        initCreateFab()
        initCreateTaskDialog()
        initCreateGroupDialog()

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.groups.observe(viewLifecycleOwner) {
            groupAdapter.groups = it.toMutableList()
            initViewPager()
        }
    }

    private fun initViewPager() {
        binding.groupViewPager2.adapter = groupAdapter

        TabLayoutMediator(binding.groupTabLayout, binding.groupViewPager2) {tab, position ->
            tab.text = groupAdapter.groups[position].name
        }.attach()
    }

    private fun initCreateFab() {
        val popupMenu = PopupMenu(requireContext(), binding.createItemFab)
        popupMenu.inflate(R.menu.create_item_menu)

        popupMenu.setOnMenuItemClickListener() {
            when(it.itemId) {
                R.id.create_task -> {
                    createTaskDialog.show()
                    true
                }
                R.id.create_group -> {
                    createGroupDialog.show()
                    true
                }
                else -> false
            }
        }

        binding.createItemFab.setOnClickListener {
            popupMenu.show()
        }

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
                group = (binding.groupTabLayout.getTabAt(
                    binding.groupTabLayout.selectedTabPosition
                )?.text ?: "").toString()
            )
            viewModel.addTask(task)

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

    private fun initCreateGroupDialog() {

        createGroupDialog = BottomSheetDialog(requireContext(), R.style.DialogStyle)
        createGroupDialog.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)

        val dialogBinding = CreateGroupDialogBinding.inflate(LayoutInflater.from(requireContext()), null, false)

        dialogBinding.groupNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                dialogBinding.saveGroupButton.isEnabled = s?.isNotBlank() == true
            }
        })

        dialogBinding.saveGroupButton.setOnClickListener {
            Toast.makeText(requireContext(), "Create", Toast.LENGTH_SHORT).show()
            val group = Group(
                name = dialogBinding.groupNameEditText.text.toString()
            )
            viewModel.addGroup(group)
            createGroupDialog.dismiss()
        }
        dialogBinding.groupNameEditText.requestFocus()

        createGroupDialog.setOnDismissListener {
            dialogBinding.apply {
                groupNameEditText.text.clear()
            }
        }

        createGroupDialog.setContentView(dialogBinding.root)
    }

    override fun getTaskByGroup(group: Group, adapter: TasksAdapter) {
        viewModel.getTasksByGroup(group, adapter, viewLifecycleOwner)
    }

    override fun updateTask(task: Task) {
        viewModel.updateTask(task)
    }

    override fun taskItemPressed(id: UUID) {
        val f = TaskDetailFragment.newInstance(id)
        navigator?.launch(f)
    }

}