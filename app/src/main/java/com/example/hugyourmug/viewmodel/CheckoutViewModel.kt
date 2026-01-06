package com.example.hugyourmug.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.hugyourmug.data.model.CartItem
import com.example.hugyourmug.data.model.Order
import com.example.hugyourmug.data.model.OrderItem
import com.example.hugyourmug.data.repository.CartRepository
import com.example.hugyourmug.data.repository.OrderRepository
import com.example.hugyourmug.ui.notifications.OrderConfirmationWorker
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class CheckoutViewModel(application: Application) : AndroidViewModel(application) {

    private val cartRepository = CartRepository()
    private val orderRepository = OrderRepository()

    private val _cartItems = MutableLiveData<List<CartItem>>()
    val cartItems: LiveData<List<CartItem>> = _cartItems

    private val _orderPlaced = MutableLiveData<Boolean>()
    val orderPlaced: LiveData<Boolean> = _orderPlaced

    fun loadCart() {
        viewModelScope.launch {
            _cartItems.value = cartRepository.getCartItems()
        }
    }

    fun subtotal(): Double {
        return _cartItems.value?.sumOf { it.price * it.quantity } ?: 0.0
    }

    fun placeOrder(
        fullName: String,
        address: String,
        isDelivery: Boolean,
        bringChange: Boolean,
        mood: String
    ) {
        val items = _cartItems.value ?: return

        viewModelScope.launch {
            val subtotal = items.sumOf { it.price * it.quantity }
            val tax = subtotal * 0.10
            val deliveryFee = if (isDelivery) 1.0 else 0.0
            val total = subtotal + tax + deliveryFee

            val order = Order(
                fullName = fullName,
                address = address,
                delivery = isDelivery,
                bringChange = bringChange,
                total = total,
                timestamp = System.currentTimeMillis(),
                mood = mood
            )

            val orderItems = items.map {
                OrderItem(
                    coffeeId = it.coffeeId,
                    name = it.name,
                    price = it.price,
                    quantity = it.quantity
                )
            }

            orderRepository.placeOrder(order, orderItems)
            cartRepository.clearCart()

            _cartItems.value = emptyList()
            _orderPlaced.value = true

            val workRequest =
                OneTimeWorkRequestBuilder<OrderConfirmationWorker>()
                    .setInitialDelay(25, TimeUnit.SECONDS)
                    .build()

            WorkManager
                .getInstance(getApplication())
                .enqueue(workRequest)
        }
    }
}
