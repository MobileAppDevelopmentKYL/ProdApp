package com.main.prodapp.fragments.todo

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
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.main.prodapp.database.TodoData
import com.main.prodapp.databinding.FragmentTodoListBinding
import com.main.prodapp.helpers.FirebaseHelper
import com.main.prodapp.viewModel.TodoListViewModel
import com.main.prodapp.viewModel.TodoListViewModelFactory
import kotlinx.coroutines.launch
import java.io.File
import java.util.Date

private const val TAG = "TodoListFragment"

class TodoListFragment : Fragment() {

    private lateinit var todoRecyclerView: RecyclerView
    private var currentFilePath: String? = null

    private val todoListViewModel: TodoListViewModel by viewModels {
        TodoListViewModelFactory("Title")
    }

    private lateinit var adapter: TodoListAdapter
    private lateinit var db: FirebaseFirestore

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

        db = FirebaseHelper.db

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
            onCapture = { todoData ->

                currentTodoData = todoData

                val imageFile = "IMG_${Date()}.JPG"
                val imageTransfer = File(requireContext().applicationContext.filesDir, imageFile)

                currentFilePath = imageTransfer.absolutePath
                currentImage = FileProvider.getUriForFile(requireContext(), "com.main.prodapp.savepicture" ,imageTransfer)
                takePictureLauncher.launch(currentImage)
            }

        )

        binding.todoRecyclerView.adapter = adapter

        binding.buttonAdd.setOnClickListener{
            val title = binding.editTextTitle.text.toString()
            val desc = binding.editTextDes.text.toString()
            if(title.isNotEmpty() && desc.isNotEmpty()){

                val task = hashMapOf("taskID1" to hashMapOf("title" to title, "description" to desc))

                db.collection("tasks").add(task)
                    .addOnSuccessListener { documentReference ->
                        Log.d("TASKS", "DocumentSnapshot added with ID: ${documentReference.id}")

                        val newTodo = TodoData(taskID = documentReference.id, title = title, description = desc, isCompleted = false)
                        showNewItem(newTodo)
                    }

                binding.editTextTitle.text.clear()
                binding.editTextDes.text.clear()
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
                    binding.todoRecyclerView.adapter =
                        TodoListAdapter(
                            todoList,
                            onDelete = { todoData -> deleteTodoItem(todoData) },
                            onUpdate = { todoData -> updateData(todoData) },
                            onCapture = { todoData ->

                                currentTodoData = todoData


                                val imageFile = "IMG_${Date()}.JPG"
                                val imageTransfer = File(requireContext().applicationContext.filesDir, imageFile)
                                currentFilePath = imageTransfer.absolutePath

                                currentImage = FileProvider.getUriForFile(requireContext(), "com.main.prodapp.savepicture" ,imageTransfer)
                                takePictureLauncher.launch(currentImage)
                            }
                        )
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

        db.collection("tasks").document(todoData.taskID).delete()
            .addOnSuccessListener { Log.d("Tasks", "DocumentSnapshot ${todoData.taskID}") }
            .addOnFailureListener { e -> Log.w("Tasks", "Error deleting document", e) }

        viewLifecycleOwner.lifecycleScope.launch {
            todoListViewModel.removeTodo(todoData)
        }
    }

    private fun updateData(todoData: TodoData) {

        binding.updateButton.visibility = View.VISIBLE
        binding.buttonAdd.visibility = View.GONE

        binding.editTextTitle.setText(todoData.title)

        binding.updateButton.setOnClickListener {

            val newDescription = binding.editTextDes.text.toString()

            val newTodo = TodoData(todoData.title, newDescription, false)
            viewLifecycleOwner.lifecycleScope.launch {
                todoListViewModel.updateTodo(newTodo)
            }

            binding.updateButton.visibility = View.GONE
            binding.buttonAdd.visibility = View.VISIBLE

            binding.editTextTitle.setText("")
            binding.editTextDes.setText("")
        }
    }

//    @Composable
//    private fun InputDialog(onDismissRequest: () -> Unit) {
//        Dialog(onDismissRequest = { onDismissRequest }) {
//            Card(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(200.dp)
//                    .padding(16.dp),
//                shape = RoundedCornerShape(16.dp),
//            ) {
//                Text(
//                    text = "This new",
//                    modifier = Modifier
//                        .fillMaxSize()
//                        .wrapContentSize(Alignment.Center),
//                    textAlign = TextAlign.Center,
//                )
//            }
//        }
//    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_todo_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_crime -> {
                showNewItem()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }*/

    private fun showNewItem(newItem: TodoData) {
        viewLifecycleOwner.lifecycleScope.launch {
            todoListViewModel.addTodo(newItem)
            /*findNavController().navigate(
                todoListFragmentDirections.showCrimeDetail(todoData.title)
            )*/
        }
    }
}