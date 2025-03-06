package com.main.prodapp.database

import androidx.room.Dao
import androidx.room.Query
import com.main.prodapp.fragments.TodoData
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoListDAO {
    @Query("SELECT * FROM tododata")
    fun getTodoList(): Flow<List<TodoData>>

    @Query("SELECT * FROM tododata WHERE title = :title")
    suspend fun getTodoItem(title: String): TodoData
}
