package com.example.hugyourmug.data.repository

import com.example.hugyourmug.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ProfileRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    suspend fun getUser(): User? {
        val userId = auth.currentUser?.uid ?: return null
        val doc = db.collection("users").document(userId).get().await()
        return doc.toObject(User::class.java)?.copy(id = doc.id)
    }
}
