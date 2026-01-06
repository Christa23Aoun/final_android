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

            val mood = rawMood.lowercase().trim()

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

            val orderedIdsForMood = orders
                .filter { it.mood.lowercase() == mood }
                .flatMap { order ->
                    orderRepository.getOrderItems(order.id).map { it.coffeeId }
                }
                .toSet()

            val orderedIdsAllTime = orders
                .flatMap { order ->
                    orderRepository.getOrderItems(order.id).map { it.coffeeId }
                }
                .toSet()

            val ranked = coffees.map { coffee ->
                var score = 0

                if (coffee.moods.any { it.lowercase() == mood }) score += 5
                if (orderedIdsAllTime.contains(coffee.id)) score += 3
                if (orderedIdsForMood.contains(coffee.id)) score += 5
                if (favoriteIds.contains(coffee.id)) score += 7

                coffee to score
            }

            val result = ranked
                .sortedByDescending { it.second }
                .map { it.first }
                .take(5)

            _suggestedCoffees.value =
                if (result.isNotEmpty()) result
                else coffees.shuffled().take(5)
        }
    }
}
