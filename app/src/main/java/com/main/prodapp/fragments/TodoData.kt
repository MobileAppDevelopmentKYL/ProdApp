package com.main.prodapp.fragments

import androidx.room.Entity

@Entity
data class TodoData (
    val title: String,
    val description: String,
    val isCompleted: Boolean = false

)

