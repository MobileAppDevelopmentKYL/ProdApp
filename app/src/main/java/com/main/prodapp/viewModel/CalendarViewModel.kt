package com.main.prodapp.viewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.main.prodapp.database.TodoData
import com.main.prodapp.database.TodoListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

private const val TAG = "CalendarViewModel"

class CalendarViewModel : ViewModel() {

    private val todoListRepo = TodoListRepository.get()
    private val _todoList: MutableStateFlow<List<TodoData>> = MutableStateFlow(emptyList())
    val todoList: StateFlow<List<TodoData>> = _todoList.asStateFlow()
    private var displayedList: List<TodoData> = emptyList()

    init {
        Log.d(TAG, "ViewModel instance created")
        viewModelScope.launch {
            todoListRepo.getTodoList().collect {
                _todoList.value = it
            }
        }
    }

    fun getTodoList(): List<TodoData> {
        return todoList.value
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private val _selectedDate = MutableLiveData(LocalDate.now())
    val selectedDate: LiveData<LocalDate> @RequiresApi(Build.VERSION_CODES.O)
    get() = _selectedDate

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateSelectedDate(newDate: LocalDate) {
        _selectedDate.value = newDate
        Log.d(TAG, newDate.toString())
    }

    fun addDisplayItem(data: TodoData){
        displayedList = displayedList + data
    }

    fun clearList(){
        displayedList = emptyList()
    }
}
