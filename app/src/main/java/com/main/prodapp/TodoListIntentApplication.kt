package com.main.prodapp

import android.app.Application
import com.main.prodapp.database.TodoListRepository

class TodoListIntentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TodoListRepository.initialize(this)
    }
}