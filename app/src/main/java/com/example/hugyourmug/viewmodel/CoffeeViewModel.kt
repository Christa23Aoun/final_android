package com.example.hugyourmug.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hugyourmug.data.model.Coffee
import com.example.hugyourmug.data.repository.CoffeeRepository
import com.example.hugyourmug.data.repository.FavoriteRepository
import com.example.hugyourmug.data.repository.OrderRepository
import kotlinx.coroutines.launch

class CoffeeViewModel : ViewModel() {

    private val coffeeRepository = CoffeeRepository()
    private val orderRepository = OrderRepository()
    private val favoriteRepository = FavoriteRepository()

    private val _allCoffees = MutableLiveData<List<Coffee>>()
    val allCoffees: LiveData<List<Coffee>> = _allCoffees

    private val _suggestedCoffees = MutableLiveData<List<Coffee>>()
    val suggestedCoffees: LiveData<List<Coffee>> = _suggestedCoffees

    init {
        loadCoffees()
    }

    private fun loadCoffees() {
        viewModelScope.launch {
            _allCoffees.value = coffeeRepository.getAllCoffees()
        }
    }

    fun loadSuggestedCoffees(rawMood: String) {
        viewModelScope.launch {

            // ✅ Normalize mood
            val mood = rawMood.lowercase().trim()

            // ✅ Always fetch coffees (do NOT rely on LiveData timing)
            val coffees = coffeeRepository.getAllCoffees()
            if (coffees.isEmpty()) {
                _suggestedCoffees.value = emptyList()
                return@launch
            }

            val favoriteIds = favoriteRepository
                .getFavorites()
                .map { it.coffeeId }
                .toSet()

            val orders = orderRepository.getOrdersForCurrentUser()

            val orderedCoffeeIdsForMood =
                orders.filter { it.mood.lowercase() == mood }
                    .flatMap { order ->
                        orderRepository.getOrderItems(order.id).map { it.coffeeId }
                    }
                    .toSet()

            val ranked = coffees.map { coffee ->
                var score = 0

                if (favoriteIds.contains(coffee.id)) score += 3
                if (orderedCoffeeIdsForMood.contains(coffee.id)) score += 2
                if (coffee.moods.any { it.lowercase() == mood }) score += 1

                coffee to score
            }

            val result = ranked
                .sortedByDescending { it.second }
                .map { it.first }
                .take(3)

            // ✅ Always show something
            _suggestedCoffees.value =
                if (result.isNotEmpty()) result
                else coffees.shuffled().take(3)
        }
    }
}
