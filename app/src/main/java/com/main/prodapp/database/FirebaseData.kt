package com.main.prodapp.database

import com.google.firebase.firestore.DocumentId

data class UserData (
    var xp: Int = 0,
    var level: Int = 0
) {
    constructor() : this(0, 0)
}

data class Task(
    @DocumentId
    var id: String? = null,
    var title: String?,
    var description: String?,
    var imagePath: String? = null
) {
    constructor() : this(null, null, null, null)
}