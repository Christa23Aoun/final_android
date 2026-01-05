package com.example.hugyourmug.data.model

data class CartItem(
    val id: String = "",
    val coffeeId: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val imageName: String = "",
    val quantity: Int = 1
)
