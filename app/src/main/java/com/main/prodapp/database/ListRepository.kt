package com.main.prodapp.database

import kotlinx.coroutines.flow.Flow

abstract class ListRepository<T> {

    abstract fun getTodoList(): Flow<List<T>>

    abstract suspend fun getTodoItem(title: String): T

    abstract suspend fun insertTodo(todoData: T)

    abstract suspend fun removeTodo(todoData: T)

    abstract suspend fun deleteAll()

    abstract fun updateTodo(todoData: T)
}
