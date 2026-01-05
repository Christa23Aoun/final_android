package com.example.hugyourmug.data.repository

import com.example.hugyourmug.data.model.Coffee
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MenuSeeder {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("coffees")

    suspend fun seedIfEmpty() {
        val check = collection.limit(1).get().await()
        if (!check.isEmpty) return

        val items = listOf(
            Coffee("", "Espresso", 1.5, 2.0, "espresso", "energy"),
            Coffee("", "Nescafe", 3.0, 3.5, "nescafe", "study"),
            Coffee("", "Hot Chocolate", 3.0, 3.5, "hot_chocolate", "cozy"),
            Coffee("", "Cappuccino", 3.5, 4.0, "cappuccino", "balanced"),
            Coffee("", "Marshmellow Chocolate", 3.0, 3.5, "marshmellow_chocolate", "sweet"),
            Coffee("", "Flat White", 4.0, 4.99, "flat_white", "smooth"),
            Coffee("", "Caffè Latte", 4.0, 4.99, "caffe_latte", "calm"),
            Coffee("", "Chestnut Praline Latte", 4.0, 4.99, "chestnut_praline_latte", "festive"),
            Coffee("", "Caramel Brulée Latte", 4.0, 4.99, "caramel_brulee_latte", "sweet"),
            Coffee("", "Apple Crisp Oatmilk Macchiato", 5.0, 5.99, "apple_crisp_oatmilk_macchiato", "autumn"),
            Coffee("", "Caramel Macchiato", 5.0, 5.99, "caramel_macchiato", "caramel"),
            Coffee("", "Espresso Macchiato", 5.0, 5.99, "espresso_macchiato", "strong"),
            Coffee("", "Peppermint Mocha", 5.0, 5.99, "peppermint_mocha", "fresh"),
            Coffee("", "Peppermint White Chocolate Mocha", 5.0, 5.99, "peppermint_white_chocolate_mocha", "extra_sweet"),
            Coffee("", "Caffè Mocha", 5.0, 5.99, "caffe_mocha", "chocolate"),
            Coffee("", "White Chocolate Mocha", 5.0, 5.99, "white_chocolate_mocha", "luxury")
        )

        for (item in items) {
            collection.add(item).await()
        }
    }
}
