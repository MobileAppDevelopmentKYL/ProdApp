package com.main.prodapp.fragments


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.main.prodapp.TodoListViewModel
import com.main.prodapp.databinding.FragmentSettingBinding
import com.main.prodapp.databinding.FragmentTodoListBinding

private const val TAG = "TodoListFragment"

class TodoListFragment : Fragment() {

    private lateinit var todoRecyclerView: RecyclerView

//    private val testTodoList = listOf(
//
//        TodoData(title = "Do homework", description = "Work on mobile apps", isCompleted = false),
//        TodoData(title = "Go to north rec", description = "Workout", isCompleted = true),
//        TodoData(title = "Sleep all day", description = "Weekend", isCompleted = false),
//
//    )
    private val todoListViewModel: TodoListViewModel by activityViewModels()
    private lateinit var adapter: TodoListAdapter

    private var _binding : FragmentTodoListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


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
        adapter = TodoListAdapter(emptyList())
        binding.todoRecyclerView.adapter = adapter

        binding.buttonAdd.setOnClickListener{
            val title = binding.editTextTitle.text.toString()
            val desc = binding.editTextDes.text.toString()
            if(title.isNotEmpty() && desc.isNotEmpty()){

                val newTodo = TodoData(title = title, description = desc, isCompleted = false)
                todoListViewModel.addTodo(newTodo)
                binding.editTextTitle.text.clear()
                binding.editTextDes.text.clear()

            }

        }




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



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
}