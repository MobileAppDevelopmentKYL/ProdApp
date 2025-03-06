package com.main.prodapp.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.main.prodapp.databinding.ListItemTodoBinding

class TodoListAdapter (private var todo: List<TodoData>) : RecyclerView.Adapter<TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): TodoViewHolder{

        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemTodoBinding.inflate(inflater, parent, false)
        return TodoViewHolder(binding)



    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int){

        val todoItem = todo[position]

        holder.bind(todoItem)

    }


    override fun getItemCount() = todo.size




}