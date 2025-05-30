package com.main.prodapp.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.main.prodapp.database.ListRepository
import com.main.prodapp.database.TodoListRepository
import com.main.prodapp.database.TodoData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

//private const val TAG = "TodoListViewModel"

class TodoListViewModel(title: String, private val todoListRepo: ListRepository<TodoData> = TodoListRepository.get()) : ViewModel() {

    private val _todoList: MutableStateFlow<List<TodoData>> = MutableStateFlow(emptyList())
    val todoList: StateFlow<List<TodoData>> = _todoList.asStateFlow()

    private val _todoItem : MutableStateFlow<TodoData?> = MutableStateFlow(null)
    val todoItem : StateFlow<TodoData?> = _todoItem.asStateFlow()

    init {
        //Log.d(TAG, "ViewModel instance created")
        viewModelScope.launch {
            todoListRepo.getTodoList().collect {
                _todoList.value = it
            }
            _todoItem.value = todoListRepo.getTodoItem(title)
        }
    }

    fun updateTodo(todoData: TodoData) {
        viewModelScope.launch {
            todoListRepo.updateTodo(todoData)

            _todoList.value = _todoList.value.map { if (it.title == todoData.title) todoData else it }
        }
    }

    suspend fun addTodo(todoData: TodoData) {
        viewModelScope.launch {
            todoListRepo.insertTodo(todoData)
            _todoList.value += todoData
        }
    }

    suspend fun removeTodo(todoData: TodoData) {
        viewModelScope.launch {
            todoListRepo.removeTodo(todoData)
            _todoList.value -= todoData
        }
    }

    override fun onCleared() {
        super.onCleared()
        //Log.d(TAG, "ViewModel instance about to be destroyed")

        viewModelScope.launch {
            todoItem.value?.let{todoListRepo.updateTodo(it)}
        }
    }
}

class TodoListViewModelFactory(
    private val title: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodoListViewModel(title) as T
    }
}