package com.main.prodapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TodoData::class], version = 3)
abstract class TodoListDatabase : RoomDatabase() {
    abstract fun todoListDao() : TodoListDAO
}