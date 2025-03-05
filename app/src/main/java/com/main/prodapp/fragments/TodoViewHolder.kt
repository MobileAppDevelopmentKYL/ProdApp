package com.main.prodapp.fragments
import com.main.prodapp.fragments.TodoData
import androidx.recyclerview.widget.RecyclerView
import com.main.prodapp.databinding.ListItemTodoBinding

class TodoViewHolder (val binding: ListItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bind(todoData: TodoData){
        binding.todoTitleText.text = todoData.title
        binding.todoDescriptionText.text = todoData.description
        binding.todoCheckBox.isChecked = todoData.isCompleted

    }



}