package com.main.prodapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TodoData::class], version = 3)
abstract class TodoListDatabase : RoomDatabase() {
    abstract fun todoListDao() : TodoListDAO

    companion object {
        @Volatile
        private var INSTANCE: TodoListDatabase? = null

        fun getInstance(context: Context): TodoListDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoListDatabase::class.java,
                    "todolist_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}