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
    val todoList: StateFlow<List<TodoData>> = _todoList.asStateFlow()

    private val _todoItem : MutableStateFlow<TodoData?> = MutableStateFlow(null)
    val todoItem : StateFlow<TodoData?> = _todoItem.asStateFlow()

    init {
        Log.d(TAG, "ViewModel instance created")
        viewModelScope.launch {
            todoListRepo.getTodoList().collect {
                _todoList.value = it
            }
        }
    }

    fun loadTodoItem(title: String) {
        viewModelScope.launch {
            _todoItem.value = todoListRepo.getTodoItem(title)
        }
    }

    fun updateTodo(todoData: TodoData) {
        viewModelScope.launch {
            todoListRepo.updateTodo(todoData)

            _todoList.value = _todoList.value.map { if (it.title == todoData.title) todoData else it }
        }
    }

    fun addTodo(todoData: TodoData) {
        viewModelScope.launch {
            todoListRepo.insertTodo(todoData)

            _todoList.value += todoData
        }
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

    //    viewModelScope.launch {
    //        todoList.value?.let{todoListRepo.updateTodo(it)}
    //    }
    }
}
