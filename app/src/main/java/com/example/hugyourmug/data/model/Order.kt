package com.example.hugyourmug.data.model

data class Order(
    val id: String = "",
    val userId: String = "",
    val fullName: String = "",
    val address: String = "",
    val isDelivery: Boolean = false,
    val bringChange: Boolean = false,
    val total: Double = 0.0,
    val timestamp: Long = 0L,
    val mood: String = "",
    val pointsEarned: Int = 0,
    val freeItemUsed: Boolean = false,
    val freeItemValue: Double = 0.0
)
