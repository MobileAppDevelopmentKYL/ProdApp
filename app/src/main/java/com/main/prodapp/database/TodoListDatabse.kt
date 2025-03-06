package com.main.prodapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.main.prodapp.fragments.TodoData

@Database(entities = [TodoData::class], version = 1)
abstract class TodoListDatabase : RoomDatabase() {
    abstract fun todoListDao() : TodoListDAO
}