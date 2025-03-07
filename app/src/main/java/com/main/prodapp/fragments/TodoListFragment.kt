package com.main.prodapp.fragments


import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Visibility
import com.main.prodapp.TodoListViewModel
import com.main.prodapp.TodoListViewModelFactory
import com.main.prodapp.databinding.FragmentTodoListBinding
import kotlinx.coroutines.launch

private const val TAG = "TodoListFragment"

class TodoListFragment : Fragment() {

    private lateinit var todoRecyclerView: RecyclerView

    private val todoListViewModel: TodoListViewModel by viewModels {
        TodoListViewModelFactory("Title")
}

    private lateinit var adapter: TodoListAdapter

    private var _binding : FragmentTodoListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

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
            onUpdate = { todoData -> updateData(todoData) }
        )

        binding.todoRecyclerView.adapter = adapter

        binding.buttonAdd.setOnClickListener{
            val title = binding.editTextTitle.text.toString()
            val desc = binding.editTextDes.text.toString()
            if(title.isNotEmpty() && desc.isNotEmpty()){

                val newTodo = TodoData(title = title, description = desc, isCompleted = false)
                showNewItem(newTodo)
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
                            onUpdate = { todoData -> updateData(todoData) }
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