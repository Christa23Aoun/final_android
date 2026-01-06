package com.example.hugyourmug.data.model

data class Order(
    val id: String = "",
    val userId: String = "",
    val fullName: String = "",
    val address: String = "",
    val delivery: Boolean = false,
    val bringChange: Boolean = false,

    val subtotal: Double = 0.0,
    val tax: Double = 0.0,
    val deliveryFee: Double = 0.0,
    val total: Double = 0.0,

    val timestamp: Long = 0L,
    val mood: String = "",

    val pointsEarned: Int = 0,
    val freeItemUsed: Boolean = false,
    val freeItemValue: Double = 0.0
)
