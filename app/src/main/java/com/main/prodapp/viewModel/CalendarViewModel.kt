package com.main.prodapp.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.time.LocalDate

private const val TAG = "CalendarViewModel"

class CalendarViewModel : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    private val _selectedDate = MutableLiveData(LocalDate.now())
    val selectedDate: LiveData<LocalDate> @RequiresApi(Build.VERSION_CODES.O)
    get() = _selectedDate

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateSelectedDate(newDate: LocalDate) {
        _selectedDate.value = newDate
        Log.d(TAG, newDate.toString())
    }
}
