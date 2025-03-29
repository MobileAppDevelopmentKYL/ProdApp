package com.main.prodapp.helpers

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.firestore.toObjects
import com.google.firebase.ktx.Firebase
import com.main.prodapp.database.Task
import com.main.prodapp.database.UserData
import kotlinx.coroutines.tasks.await

private const val TAG = "FIREBASESERVICE"

object FirebaseService {
    val db: FirebaseFirestore by lazy { Firebase.firestore }
    val auth: FirebaseAuth by lazy { Firebase.auth }

    suspend fun createUserData(userData: UserData) {
        val userID = auth.currentUser?.uid ?: return
        val userDocRef = db.collection("users").document(userID)

        userDocRef.set(userData, SetOptions.merge()).await()
    }

    suspend fun getUserData(): UserData? {
        val userID = auth.currentUser?.uid ?: return null
        val userDocRef = db.collection("users").document(userID)

        val snapshot = userDocRef.get().await()
        return snapshot.toObject<UserData>()
    }

    private fun getTasksCollectionRef() : CollectionReference? {
        return auth.currentUser?.uid?.let { userId ->
             db.collection("users").document(userId).collection("tasks")
        }
    }

    suspend fun getCurrentUserTasks() : List<Task> {
        val taskCollection = getTasksCollectionRef()
        val snapshot = taskCollection?.get()?.await()

        return snapshot?.toObjects<Task>() ?: emptyList()
    }

    suspend fun addTask(task : Task): String {
        val tasksCollection = getTasksCollectionRef()
        val docRef = tasksCollection?.add(task)?.await()

        Log.d(TAG, "TASK ADDED ${docRef?.id}")

        return docRef?.id ?: "none"
    }

    suspend fun updateTask(taskID: String, description: String) {
        val tasksCollection = getTasksCollectionRef()
        tasksCollection?.document(taskID)?.update("description",description)?.await()
    }

    suspend fun deleteTask(taskID : String) {
        val taskCollection = getTasksCollectionRef()
        taskCollection?.document(taskID)?.delete()
    }

}