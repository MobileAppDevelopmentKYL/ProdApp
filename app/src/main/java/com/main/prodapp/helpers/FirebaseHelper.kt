package com.main.prodapp.helpers

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object FirebaseHelper {
    val db: FirebaseFirestore by lazy { Firebase.firestore }
}