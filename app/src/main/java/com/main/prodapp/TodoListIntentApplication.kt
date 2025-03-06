package com.main.prodapp

import android.app.Application

class TodoListIntentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TodoListRepository.initialize(this)
    }
}