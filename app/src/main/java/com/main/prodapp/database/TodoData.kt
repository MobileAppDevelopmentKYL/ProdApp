package com.main.prodapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class TodoData (
    @PrimaryKey val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    var imagePath: String? = null,
    var targetDate: Long?

)

