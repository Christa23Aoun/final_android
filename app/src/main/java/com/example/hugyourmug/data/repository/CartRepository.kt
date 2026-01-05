package com.example.hugyourmug.data.repository

import com.example.hugyourmug.data.model.CartItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class CartRepository {

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    private fun cartRef() =
        db.collection("carts")
            .document(auth.currentUser?.uid ?: "")
            .collection("items")

    suspend fun addToCart(item: CartItem) {
        val existing = cartRef()
            .whereEqualTo("coffeeId", item.coffeeId)
            .get()
            .await()

        if (existing.documents.isNotEmpty()) {
            val doc = existing.documents.first()
            val qty = doc.getLong("quantity") ?: 1
            doc.reference.update("quantity", qty + 1).await()
        } else {
            cartRef().add(item.copy(id = "")).await()
        }
    }

    suspend fun getCartItems(): List<CartItem> {
        val snapshot = cartRef().get().await()
        return snapshot.documents.mapNotNull { doc ->
            val item = doc.toObject(CartItem::class.java)
            item?.copy(id = doc.id)
        }
    }

    suspend fun updateQuantity(itemId: String, quantity: Int) {
        cartRef().document(itemId).update("quantity", quantity).await()
    }

    suspend fun removeItem(itemId: String) {
        cartRef().document(itemId).delete().await()
    }

    suspend fun clearCart() {
        val snapshot = cartRef().get().await()
        snapshot.documents.forEach { it.reference.delete().await() }
    }
}
