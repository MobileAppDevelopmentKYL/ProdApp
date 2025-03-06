package com.main.prodapp.database

import androidx.room.Dao
import androidx.room.Query
import com.main.prodapp.fragments.TodoData

@Dao
interface TodoListDAO {
    @Query("SELECT * FROM tododata")
    suspend fun getTodoList(): List<TodoData>

    @Query("SELECT * FROM tododata WHERE title = :title")
    suspend fun getTodoItem(title: String): TodoData
}
