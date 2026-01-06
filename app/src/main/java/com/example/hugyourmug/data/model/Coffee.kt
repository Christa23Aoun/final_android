package com.example.hugyourmug.data.model

data class Coffee(
    val id: String = "",
    val name: String = "",
    val smallPrice: Double = 0.0,
    val largePrice: Double = 0.0,
    val imageName: String = "",
    val moods: List<String> = emptyList()
)
