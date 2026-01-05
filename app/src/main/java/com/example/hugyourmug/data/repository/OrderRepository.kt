package com.example.hugyourmug.data.repository

import com.example.hugyourmug.data.model.Order
import com.example.hugyourmug.data.model.OrderItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class OrderRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()
    private val ordersCollection = firestore.collection("orders")

    private fun currentUserId(): String {
        return auth.currentUser?.uid
            ?: throw IllegalStateException("User not logged in")
    }

    suspend fun placeOrder(order: Order, items: List<OrderItem>) {
        val userId = currentUserId()
        val orderWithUser = order.copy(userId = userId)

        val orderRef = ordersCollection.add(orderWithUser).await()
        val itemsCollection = orderRef.collection("items")

        items.forEach { item ->
            itemsCollection.add(
                item.copy(orderId = orderRef.id)
            ).await()
        }
    }

    suspend fun getOrdersForCurrentUser(): List<Order> {
        val userId = currentUserId()

        val snapshot = ordersCollection
            .whereEqualTo("userId", userId)
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(Order::class.java)?.copy(id = doc.id)
        }
    }

    suspend fun getOrderById(orderId: String): Order? {
        val doc = ordersCollection.document(orderId).get().await()
        return doc.toObject(Order::class.java)?.copy(id = doc.id)
    }

    suspend fun getOrderItems(orderId: String): List<OrderItem> {
        val snapshot = ordersCollection
            .document(orderId)
            .collection("items")
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            doc.toObject(OrderItem::class.java)?.copy(id = doc.id)
        }
    }
}
