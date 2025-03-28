package com.main.prodapp.database

import com.google.firebase.firestore.DocumentId

data class UserData (
    var userID: String?
) {
    constructor() : this(null)
}

data class Task(
    @DocumentId
    var id: String? = null,
    var title: String?,
    var description: String?
) {
    constructor() : this(null, null, null)
}