package com.main.prodapp.viewModel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.main.prodapp.database.ListRepository
import com.main.prodapp.database.TodoData
import com.main.prodapp.database.TodoListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

//private const val TAG = "CalendarViewModel"

class CalendarViewModel(
    private val todoListRepo: ListRepository<TodoData> = TodoListRepository.get()
) : ViewModel() {
    private val _todoList: MutableStateFlow<List<TodoData>> = MutableStateFlow(emptyList())
    private val todoList: StateFlow<List<TodoData>> = _todoList.asStateFlow()
    private var displayedList: List<TodoData> = emptyList()

    init {

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

    }

    fun addDisplayItem(data: TodoData){
        displayedList = displayedList + data
    }

    fun clearList(){
        displayedList = emptyList()
    }
}


class CalendarViewModelFactory(
    private val repo: TodoListRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CalendarViewModel(repo) as T
    }
}

