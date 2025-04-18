package com.main.prodapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoListDAO {
    @Query("SELECT * FROM tododata")
    fun getTodoList(): Flow<List<TodoData>>

    @Query("SELECT * FROM tododata WHERE title = :title")
    suspend fun getTodoItem(title: String): TodoData

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todoData: TodoData)

    @Update
    suspend fun updateTodo(todoData: TodoData)

    @Delete
    suspend fun removeTodo(todoData: TodoData)

    @Query("DELETE FROM tododata")
    suspend fun deleteAll()
}
