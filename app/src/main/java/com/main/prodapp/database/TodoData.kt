package com.main.prodapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoData (
    @PrimaryKey val title: String,
    val description: String,
    val isCompleted: Boolean = false

)

