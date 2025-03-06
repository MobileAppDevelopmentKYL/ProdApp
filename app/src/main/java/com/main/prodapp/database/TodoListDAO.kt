package com.main.prodapp.database

import androidx.room.Dao
import androidx.room.Query
import com.main.prodapp.fragments.TodoData

@Dao
interface TodoListDAO {
    @Query("SELECT * FROM tododata")
    suspend fun getTodoItem(): List<TodoData>
}
