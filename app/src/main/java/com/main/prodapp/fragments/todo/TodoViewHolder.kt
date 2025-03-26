package com.main.prodapp.fragments.todo
import android.graphics.BitmapFactory
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.main.prodapp.database.TodoData
import com.main.prodapp.databinding.ListItemTodoBinding

class TodoViewHolder (
    private val binding: ListItemTodoBinding,
    private val onDelete: (TodoData) -> Unit,
    private val onUpdate: (TodoData) -> Unit,
    private val onCapture: (TodoData) -> Unit): RecyclerView.ViewHolder(binding.root) {

    fun bind(todoData: TodoData) {
        binding.todoTitleText.text = todoData.title
        binding.todoDescriptionText.text = todoData.description
        binding.todoCheckBox.isChecked = todoData.isCompleted


        binding.root.setOnClickListener {
            onUpdate(todoData)
        }

        binding.todoCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                onDelete(todoData)
            }
        }

        binding.buttonCapture.setOnClickListener {
            onCapture(todoData)
        }



        if(!todoData.imagePath.isNullOrEmpty()){
            binding.imageView2.visibility = View.VISIBLE
            val bitmap = BitmapFactory.decodeFile(todoData.imagePath)
            binding.imageView2.setImageBitmap(bitmap)
        }else{
            binding.imageView2.visibility = View.GONE
        }



    }
}