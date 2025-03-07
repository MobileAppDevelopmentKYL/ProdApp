package com.main.prodapp.fragments.todo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.main.prodapp.database.TodoData
import com.main.prodapp.databinding.ListItemTodoBinding

class TodoListAdapter (
    private var todo: List<TodoData>,
    private var onDelete: (TodoData) -> Unit,
    private var onUpdate: (TodoData) -> Unit) : RecyclerView.Adapter<TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemTodoBinding.inflate(inflater, parent, false)
        return TodoViewHolder(binding, onDelete, onUpdate)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int){
        val todoItem = todo[position]
        holder.bind(todoItem)
    }

    override fun getItemCount() = todo.size
}