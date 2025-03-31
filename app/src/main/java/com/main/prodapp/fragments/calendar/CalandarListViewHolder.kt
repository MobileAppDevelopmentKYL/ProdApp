package com.main.prodapp.fragments.calendar

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.main.prodapp.R
import com.main.prodapp.database.TodoData

class CalendarListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val titleTextView: TextView = itemView.findViewById(R.id.todoTitleText)
    private val descriptionTextView: TextView = itemView.findViewById(R.id.todoDescriptionText)

    fun bind(todoData: TodoData) {
        titleTextView.text = todoData.title  // Assuming TodoData has a 'title' property
        descriptionTextView.text = todoData.description // Assuming TodoData has a 'description' property
    }
}
