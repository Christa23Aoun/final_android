package com.example.hugyourmug.data.repository

import com.example.hugyourmug.data.model.FavoriteItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class FavoriteRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private fun favRef() =
        db.collection("favorites")
            .document(auth.currentUser?.uid ?: "")
            .collection("items")

    suspend fun getFavorites(): List<FavoriteItem> {
        val snapshot = favRef().get().await()
        return snapshot.documents.mapNotNull { doc ->
            val item = doc.toObject(FavoriteItem::class.java)
            item?.copy(id = doc.id)
        }
    }

    suspend fun toggleFavorite(item: FavoriteItem) {
        val existing = favRef()
            .whereEqualTo("coffeeId", item.coffeeId)
            .get()
            .await()

        if (existing.documents.isNotEmpty()) {
            existing.documents.first().reference.delete().await()
        } else {
            favRef().add(item.copy(id = "")).await()
        }
    }

    suspend fun removeFavorite(favoriteId: String) {
        favRef().document(favoriteId).delete().await()
    }
}
