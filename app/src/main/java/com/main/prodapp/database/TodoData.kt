package com.main.prodapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoData (
    @PrimaryKey val taskID: String,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    var imagePath: String? = null
)

