package com.main.prodapp

import android.content.Context
import androidx.room.Room
import com.main.prodapp.database.TodoListDatabase
import com.main.prodapp.fragments.TodoData
import kotlinx.coroutines.flow.Flow

private const val DATABASE_NAME = "todolist-database"

class TodoListRepository private constructor(context: Context) {

    private val database: TodoListDatabase = Room
        .databaseBuilder(
            context.applicationContext,
            TodoListDatabase::class.java,
            DATABASE_NAME
        )
        .createFromAsset(DATABASE_NAME)
        .build()

    fun getTodoList(): Flow<List<TodoData>> = database.todoListDao().getTodoList()
    suspend fun getTodoItem(title: String): TodoData = database.todoListDao().getTodoItem(title)

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