package com.main.prodapp

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.main.prodapp.fragments.TodoData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "TodoListViewModel"

class TodoListViewModel : ViewModel() {

    //private var todoList:List<TodoData> = emptyList()
    private val todoListRepo = TodoListRepository.get()
    private val _todoList: MutableStateFlow<List<TodoData>> = MutableStateFlow(emptyList())
    val todoList: StateFlow<List<TodoData>>
        get() = _todoList.asStateFlow()

    init {
        Log.d(TAG, "ViewModel instance created")
        viewModelScope.launch {
            todoListRepo.getTodoList().collect {
                _todoList.value = it
            }
        }
    }

    fun addTodo(todo: TodoData) {
        _todoList.value = _todoList.value + todo
        Log.d(TAG, "item added: $todo")
    }

//    fun removeTodo(todo: TodoData) {
//        todoList = todoList - todo
//        Log.d(TAG, "item removed: $todo")
//    }
//
//    fun getTodos(): List<TodoData> {
//        return todoList
//    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }
}
