package com.example.hugyourmug.data.model

import androidx.annotation.DrawableRes

data class CoffeeMenuItem(
    val name: String,
    val smallPrice: Double,
    val largePrice: Double,
    @DrawableRes val imageRes: Int,
    val tag: String
)
