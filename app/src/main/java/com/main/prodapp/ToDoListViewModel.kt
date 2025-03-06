package com.main.prodapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.main.prodapp.fragments.TodoData

private const val TAG = "ToDoListViewModel"

class ToDoListViewModel : ViewModel() {

    private var todoList:List<TodoData> = emptyList()
    private val todoListRepo = TodoListRepository.get()

    init {
        Log.d(TAG, "ViewModel instance created")
    }

    fun addTodo(todo: TodoData) {
        todoList = todoList + todo
        Log.d(TAG, "item added: $todo")
    }

    fun removeTodo(todo: TodoData) {
        todoList = todoList - todo
        Log.d(TAG, "item removed: $todo")
    }

    fun getTodos(): List<TodoData> {
        return todoList
    }

    suspend fun loadTodos(): List<TodoData> {
        return todoListRepo.getTodoList()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "ViewModel instance about to be destroyed")
    }
}
