package com.main.prodapp.helpers

import android.content.Context
import android.net.Uri
import android.nfc.Tag
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
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.main.prodapp.database.Task
import com.main.prodapp.database.UserData
import kotlinx.coroutines.tasks.await
import java.io.File

private const val TAG = "FIREBASESERVICE"

object FirebaseService {
    val db: FirebaseFirestore by lazy { Firebase.firestore }
    val auth: FirebaseAuth by lazy { Firebase.auth }
    val storage: FirebaseStorage by lazy { Firebase.storage }

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

    suspend fun updateGameData(data: HashMap<String, Any>) {
        db.collection("users").document(auth.currentUser?.uid!!).update(data)
    }

    suspend fun addTask(task : Task): String {
        val tasksCollection = getTasksCollectionRef()
        val docRef = tasksCollection?.add(task)?.await()

        Log.d(TAG, "TASK ADDED ${docRef?.id}")

        return docRef?.id ?: "none"
    }

    suspend fun updateTaskDescription(taskID: String, description: String) {
        val tasksCollection = getTasksCollectionRef()
        tasksCollection?.document(taskID)?.update("description",description)?.await()
    }

    suspend fun updateTaskImage(taskID: String, imagePath: String) {
        val tasksCollection = getTasksCollectionRef()
        val filePath = Uri.fromFile(File(imagePath)).lastPathSegment
        tasksCollection?.document(taskID)?.update("imagePath", filePath)?.await()
    }

    suspend fun deleteTask(taskID : String) {
        val taskCollection = getTasksCollectionRef()
        taskCollection?.document(taskID)?.delete()
    }

    suspend fun loadImage(context: Context, imagePath: String) {
        val storage = FirebaseService.storage
        val storageRef = storage.reference.child("images/${auth.currentUser!!.uid}/${imagePath}")

        val localFile = File(context.filesDir, imagePath)

        Log.d(TAG, "FirebaseDownload: $localFile")

        storageRef.getFile(localFile)
            .addOnSuccessListener {
                Log.d("FirebaseDownload", "Image downloaded to: ${localFile.absolutePath}")
            }
            .addOnFailureListener {
                Log.e("FirebaseDownload", "Failed to download image", it)
            }
    }

    public fun uploadImage(filePath: String, storage: FirebaseStorage) {
        if (filePath == "none") return

        val storageRef = storage.reference
        val file = Uri.fromFile(File(filePath))

        var uploadTask = storageRef.child("images/${auth.currentUser!!.uid}/${file.lastPathSegment}").putFile(file)

        uploadTask.addOnSuccessListener {
            Log.d(TAG, "Upload successful: ${it.metadata?.path}")
        }.addOnFailureListener {
            Log.e(TAG, "Upload failed", it)
        }
    }

}