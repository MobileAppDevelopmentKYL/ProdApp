package com.main.prodapp.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.main.prodapp.CalendarAdapter
import com.main.prodapp.databinding.FragmentCalendarBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

private const val TAG = "CalendarFragment"

class CalendarFragment : Fragment(), CalendarAdapter.OnItemListener {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = checkNotNull(_binding) {
        "Cannot access binding because it is null. Is the view visible?"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private var selectedDate: LocalDate = LocalDate.now()

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
        setMonthView()

        binding.prevMonthButton.setOnClickListener { previousMonthAction() }
        binding.nextMonthButton.setOnClickListener { nextMonthAction() }
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
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthView() {
        binding.monthYearTV.text = monthYearFromDate(selectedDate)
        val daysInMonth = daysInMonthArray(selectedDate)
        binding.calendarRecyclerView.adapter = CalendarAdapter(daysInMonth, this)
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
        selectedDate = selectedDate.minusMonths(1)
        setMonthView()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun nextMonthAction() {
        selectedDate = selectedDate.plusMonths(1)
        setMonthView()
    }

    private fun displayDateSelected(date: String){
        binding.dateSelectionHeader.text = "Todo Items On:"
        binding.dateSelected.text=date
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(position: Int, dayText: String) {
        if (dayText.isNotEmpty()) {
            val message = "$dayText ${monthYearFromDate(selectedDate)}"
            //TODO: Display items on that day
            displayDateSelected(message)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d(TAG, "Start onDestroyView")
    }
}
