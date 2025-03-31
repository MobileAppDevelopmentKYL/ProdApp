package com.main.prodapp.fragments.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.main.prodapp.R

class CalendarListAdapter(private val itemList: MutableList<String>) :
    RecyclerView.Adapter<CalendarListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_item, parent, false)
        return CalendarListViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarListViewHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int = itemList.size

    fun addItem(newItem: String) {
        itemList.add(newItem)
        notifyItemInserted(itemList.size - 1)
    }
}