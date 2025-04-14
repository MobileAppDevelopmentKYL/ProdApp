package com.main.prodapp.fragments.todo


import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.main.prodapp.database.Task
import com.main.prodapp.database.TodoData
import com.main.prodapp.databinding.FragmentTodoListBinding
import com.main.prodapp.helpers.FirebaseService
import com.main.prodapp.viewModel.TodoListViewModel
import com.main.prodapp.viewModel.TodoListViewModelFactory
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar

import java.util.Date
import java.util.Locale

private const val TAG = "TodoListFragment"

class TodoListFragment : Fragment() {

    private lateinit var todoRecyclerView: RecyclerView
    private var currentFilePath: String? = null

    private val todoListViewModel: TodoListViewModel by viewModels {
        TodoListViewModelFactory("Title")
    }

    private lateinit var adapter: TodoListAdapter
    private lateinit var db: FirebaseFirestore
    private lateinit var storage: FirebaseStorage

    private var _binding : FragmentTodoListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private var currentTodoData: TodoData? = null
    private var currentImage: Uri? = null

    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            currentTodoData?.let { todoData ->
                todoData.imagePath = currentFilePath

                FirebaseService.uploadImage(currentFilePath ?: "none", storage)

                viewLifecycleOwner.lifecycleScope.launch {
                    FirebaseService.updateTaskImage(todoData.taskID, currentFilePath ?: "none")
                }

                updateDataWithImage(todoData)
            }

        }
    }

    private fun updateDataWithImage(todoData: TodoData) {
        viewLifecycleOwner.lifecycleScope.launch {
            todoListViewModel.updateTodo(todoData)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        db = FirebaseService.db
        storage = FirebaseService.storage

        Log.d(TAG, "Start onCreate")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "Start onCreateView")
        _binding = FragmentTodoListBinding.inflate(inflater, container, false)

        binding.todoRecyclerView.layoutManager = LinearLayoutManager(context)

        adapter = TodoListAdapter(
            emptyList(),
            onDelete = { todoData -> deleteTodoItem(todoData) },
            onUpdate = { todoData -> updateData(todoData) },
            onCapture = { todoData -> captureImage(todoData) }
        )

        binding.todoRecyclerView.adapter = adapter

        binding.editDateButton.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                binding.editDateButton.text = selectedDate
            }, year, month, day)

            datePicker.show()
        }

        binding.buttonAdd.setOnClickListener {
            val title = binding.editTextTitle.text.toString()
            val desc = binding.editTextDes.text.toString()
            val dateStr = binding.editDateButton.text.toString()
            if (title.isNotEmpty() && desc.isNotEmpty() && dateStr != "Select Date") {

                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val date = dateFormat.parse(dateStr)

                val task = Task(title = title, description = desc)
                viewLifecycleOwner.lifecycleScope.launch {
                    val taskID = FirebaseService.addTask(task)
                    val timeConv: Long? = date?.time
                    val newTodo = TodoData(taskID = taskID, title = title, description = desc, isCompleted = false, targetDate = timeConv)
                    showNewItem(newTodo)
                }

                binding.editTextTitle.text.clear()
                binding.editTextDes.text.clear()
                binding.editDateButton.text = "Select Date"
            }

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.updateButton.visibility = View.GONE

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                todoListViewModel.todoList.collect { todoList ->
                    adapter.updateTodoList(todoList)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        Log.d(TAG, "Start onStart")
    }

    override fun onResume() {
        super.onResume()

        Log.d(TAG, "Start onResume")
    }

    override fun onPause() {
        super.onPause()

        Log.d(TAG, "Start onPause")
    }

    override fun onStop() {
        super.onStop()

        Log.d(TAG, "Start onStop")
    }

    override fun onDestroy() {
        super.onDestroy()

        Log.d(TAG, "Start onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()

        Log.d(TAG, "Start onDestoryView")
    }

    private fun deleteTodoItem(todoData: TodoData) {

        viewLifecycleOwner.lifecycleScope.launch {
            FirebaseService.deleteTask(todoData.taskID)
            todoListViewModel.removeTodo(todoData)
        }
    }

    private fun updateData(todoData: TodoData) {

        binding.updateButton.visibility = View.VISIBLE
        binding.buttonAdd.visibility = View.GONE

        binding.editTextTitle.setText(todoData.title)

        binding.updateButton.setOnClickListener {

            val newDescription = binding.editTextDes.text.toString()
            val dateVal = binding.editDateButton.toString()

            if (binding.editTextTitle.text.isNotEmpty() && newDescription.isNotEmpty() && dateVal != "Select Date") {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd")
                val date = dateFormat.parse(dateVal)

                val timeConv: Long? = date?.time

                val newTodo = TodoData(
                    todoData.taskID,
                    todoData.title,
                    newDescription,
                    false,
                    targetDate = timeConv
                )

                viewLifecycleOwner.lifecycleScope.launch {
                    FirebaseService.updateTaskDescription(todoData.taskID, newDescription)
                    todoListViewModel.updateTodo(newTodo)
                }

                binding.updateButton.visibility = View.GONE
                binding.buttonAdd.visibility = View.VISIBLE

                binding.editTextTitle.setText("")
                binding.editTextDes.setText("")
            }
        }
    }

    private fun showNewItem(newItem: TodoData) {
        viewLifecycleOwner.lifecycleScope.launch {
            todoListViewModel.addTodo(newItem)
            /*findNavController().navigate(
                todoListFragmentDirections.showCrimeDetail(todoData.title)
            )*/
        }
    }

    private fun captureImage(todoData: TodoData) {
        currentTodoData = todoData

        val imageFile = "IMG_${Date()}.JPG"
        val imageTransfer = File(requireContext().applicationContext.filesDir, imageFile)
        currentFilePath = imageTransfer.absolutePath

        currentImage = FileProvider.getUriForFile(requireContext(), "com.main.prodapp.savepicture" ,imageTransfer)
        takePictureLauncher.launch(currentImage)
    }
}