package com.main.prodapp.fragments.calendar

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.main.prodapp.R
import com.main.prodapp.database.TodoData
import com.main.prodapp.databinding.FragmentCalendarBinding
import com.main.prodapp.viewModel.CalendarViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

private const val TAG = "CalendarFragment"

class CalendarFragment : Fragment(), CalendarAdapter.OnItemListener {

    private val viewModel: CalendarViewModel by viewModels()
    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = checkNotNull(_binding) {
        "Cannot access binding because it is null. Is the view visible?"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private var selectedDate: LocalDate = LocalDate.now()

    private lateinit var listAdapter: CalendarListAdapter

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
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initWidgets()
        setMonthView(viewModel.selectedDate.value!!)
        binding.dateSelectionHeader.text = "Please Select a Date"

        binding.prevMonthButton.setOnClickListener { previousMonthAction() }
        binding.nextMonthButton.setOnClickListener { nextMonthAction() }

        val recyclerView = view.findViewById<RecyclerView>(R.id.itemsRecyclerView)
        listAdapter = CalendarListAdapter(mutableListOf())
        recyclerView.adapter = listAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        displayItems(viewModel.selectedDate.value.toString())
    }

    private fun displayItems(dateStr: String){
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val date = dateFormat.parse(dateStr)

        val timeConv: Long? = date?.time

        val items: List<TodoData> = viewModel.getTodoList()
        viewModel.clearList()
        listAdapter.clearList()
        for (data: TodoData in items){
            if (data.targetDate == timeConv){
                viewModel.addDisplayItem(data)
                listAdapter.addItem(data)
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

    private fun initWidgets() {
        binding.calendarRecyclerView.layoutManager = GridLayoutManager(requireContext(), 7)
        binding.itemsRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthView(date: LocalDate) {
        binding.monthYearTV.text = monthYearFromDate(date)
        val daysInMonth = daysInMonthArray(date)
        binding.calendarRecyclerView.adapter =
            CalendarAdapter(
                daysInMonth,
                this
            )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun daysInMonthArray(date: LocalDate): ArrayList<String> {
        val daysInMonthArray = ArrayList<String>()
        val yearMonth = YearMonth.from(date)
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value

        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        return daysInMonthArray
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun monthYearFromDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return date.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun previousMonthAction() {
        val newDate = viewModel.selectedDate.value!!.minusMonths(1)
        viewModel.updateSelectedDate(newDate)
        setMonthView(newDate)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun nextMonthAction() {
        val newDate = viewModel.selectedDate.value!!.plusMonths(1)
        viewModel.updateSelectedDate(newDate)
        setMonthView(newDate)
    }

    private fun displayDateSelected(date: LocalDate){
        binding.dateSelectionHeader.text = "Things to do on"
        binding.dateSelected.text=date.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(position: Int, dayText: String) {
        if (dayText.isNotEmpty()) {
            val newDate = LocalDate.of(
                viewModel.selectedDate.value!!.year,
                viewModel.selectedDate.value!!.month,
                dayText.toInt()
            )
            viewModel.updateSelectedDate(newDate)
            displayDateSelected(newDate)
            displayItems(newDate.toString())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(TAG, "Start onDestroyView")
    }
}