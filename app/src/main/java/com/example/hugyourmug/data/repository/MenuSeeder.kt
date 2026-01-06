package com.example.hugyourmug.data.repository

import com.example.hugyourmug.data.model.Coffee
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MenuSeeder {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("coffees")

    suspend fun seedOrUpdateMoods() {
        val snapshot = collection.get().await()

        val moodMap = mapOf(
            "Espresso" to listOf("tired", "study", "late_night", "focused"),
            "Nescafe" to listOf("study", "tired", "focused"),
            "Hot Chocolate" to listOf("sad", "relaxed", "cozy", "broken"),
            "Marshmellow Chocolate" to listOf("happy", "sad", "cozy"),
            "Cappuccino" to listOf("happy", "focused", "study"),
            "Flat White" to listOf("focused", "relaxed", "study"),
            "Caffè Latte" to listOf("relaxed", "cozy"),
            "Chestnut Praline Latte" to listOf("happy", "relaxed", "cozy"),
            "Caramel Brulée Latte" to listOf("happy", "motivated", "social"),
            "Apple Crisp Oatmilk Macchiato" to listOf("relaxed", "happy", "social"),
            "Caramel Macchiato" to listOf("happy", "motivated", "social"),
            "Espresso Macchiato" to listOf("tired", "late_night", "focused"),
            "Peppermint Mocha" to listOf("stressed", "relaxed", "cold_weather"),
            "Peppermint White Chocolate Mocha" to listOf("sad", "happy", "cozy"),
            "Caffè Mocha" to listOf("stressed", "happy", "study"),
            "White Chocolate Mocha" to listOf("sad", "relaxed", "cozy")
        )

        if (snapshot.isEmpty) {
            val items = listOf(
                Coffee("", "Espresso", 1.5, 2.0, "espresso", moods = moodMap["Espresso"] ?: emptyList()),
                Coffee("", "Nescafe", 3.0, 3.5, "nescafe", moods = moodMap["Nescafe"] ?: emptyList()),
                Coffee("", "Hot Chocolate", 3.0, 3.5, "hot_chocolate", moods = moodMap["Hot Chocolate"] ?: emptyList()),
                Coffee("", "Marshmellow Chocolate", 3.0, 3.5, "marshmellow_chocolate", moods = moodMap["Marshmellow Chocolate"] ?: emptyList()),
                Coffee("", "Cappuccino", 3.5, 4.0, "cappuccino", moods = moodMap["Cappuccino"] ?: emptyList()),
                Coffee("", "Flat White", 4.0, 4.99, "flat_white", moods = moodMap["Flat White"] ?: emptyList()),
                Coffee("", "Caffè Latte", 4.0, 4.99, "caffe_latte", moods = moodMap["Caffè Latte"] ?: emptyList()),
                Coffee("", "Chestnut Praline Latte", 4.0, 4.99, "chestnut_praline_latte", moods = moodMap["Chestnut Praline Latte"] ?: emptyList()),
                Coffee("", "Caramel Brulée Latte", 4.0, 4.99, "caramel_brulee_latte", moods = moodMap["Caramel Brulée Latte"] ?: emptyList()),
                Coffee("", "Apple Crisp Oatmilk Macchiato", 5.0, 5.99, "apple_crisp_oatmilk_macchiato", moods = moodMap["Apple Crisp Oatmilk Macchiato"] ?: emptyList()),
                Coffee("", "Caramel Macchiato", 5.0, 5.99, "caramel_macchiato", moods = moodMap["Caramel Macchiato"] ?: emptyList()),
                Coffee("", "Espresso Macchiato", 5.0, 5.99, "espresso_macchiato", moods = moodMap["Espresso Macchiato"] ?: emptyList()),
                Coffee("", "Peppermint Mocha", 5.0, 5.99, "peppermint_mocha", moods = moodMap["Peppermint Mocha"] ?: emptyList()),
                Coffee("", "Peppermint White Chocolate Mocha", 5.0, 5.99, "peppermint_white_chocolate_mocha", moods = moodMap["Peppermint White Chocolate Mocha"] ?: emptyList()),
                Coffee("", "Caffè Mocha", 5.0, 5.99, "caffe_mocha", moods = moodMap["Caffè Mocha"] ?: emptyList()),
                Coffee("", "White Chocolate Mocha", 5.0, 5.99, "white_chocolate_mocha", moods = moodMap["White Chocolate Mocha"] ?: emptyList())
            )

            for (item in items) {
                collection.add(item).await()
            }
            return
        }

        for (doc in snapshot.documents) {
            val name = doc.getString("name") ?: continue
            val moods = moodMap[name] ?: emptyList()
            collection.document(doc.id).update("moods", moods).await()
        }
    }
}
