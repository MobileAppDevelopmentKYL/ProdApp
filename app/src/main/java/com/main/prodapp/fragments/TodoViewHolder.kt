package com.main.prodapp.fragments
import android.widget.Toast
import com.main.prodapp.fragments.TodoData
import androidx.recyclerview.widget.RecyclerView
import com.main.prodapp.databinding.ListItemTodoBinding

class TodoViewHolder (val binding: ListItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {


    fun bind(todoData: TodoData){
        binding.todoTitleText.text = todoData.title
        binding.todoDescriptionText.text = todoData.description
        binding.todoCheckBox.isChecked = todoData.isCompleted

        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${todoData.title} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }

    }



}