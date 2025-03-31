package com.main.prodapp.fragments.calendar

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.main.prodapp.R

class CalendarListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textView: TextView = itemView.findViewById(R.id.textView)

    fun bind(text: String) {
        textView.text = text
    }
}