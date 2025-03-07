package com.main.prodapp

import android.content.Context
import androidx.room.Room
import com.main.prodapp.database.TodoListDatabase
import com.main.prodapp.fragments.TodoData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

private const val DATABASE_NAME = "todolist_database"

class TodoListRepository @OptIn(DelicateCoroutinesApi::class)
private constructor(
    context: Context,
    private val coroutineScope : CoroutineScope = GlobalScope
) {

    private val database: TodoListDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            TodoListDatabase::class.java,
            DATABASE_NAME
        )
        .build()

    fun getTodoList(): Flow<List<TodoData>> = database.todoListDao().getTodoList()
    suspend fun getTodoItem(title: String): TodoData = database.todoListDao().getTodoItem(title)

    fun updateTodo(todoData: TodoData){
        coroutineScope.launch {
            database.todoListDao().updateTodo(todoData)
        }
    }

    suspend fun insertTodo(todoData: TodoData) {
        database.todoListDao().insertTodo(todoData)
    }

    suspend fun removeTodo(todoData: TodoData) {
        database.todoListDao().removeTodo(todoData)
    }

    companion object {
        private var INSTANCE: TodoListRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = TodoListRepository(context)
            }
        }

        fun get(): TodoListRepository {
            return INSTANCE ?:
            throw IllegalStateException("TodoListRepository must be initialized")
        }
    }
}