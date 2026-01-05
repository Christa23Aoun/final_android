package com.example.hugyourmug.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hugyourmug.data.model.CartItem
import com.example.hugyourmug.data.model.FavoriteItem
import com.example.hugyourmug.data.repository.CartRepository
import com.example.hugyourmug.data.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoritesViewModel : ViewModel() {

    private val favoriteRepository = FavoriteRepository()
    private val cartRepository = CartRepository()

    private val _favorites = MutableLiveData<List<FavoriteItem>>()
    val favorites: LiveData<List<FavoriteItem>> = _favorites

    fun loadFavorites() {
        viewModelScope.launch {
            _favorites.value = favoriteRepository.getFavorites()
        }
    }

    fun toggleFavorite(item: FavoriteItem) {
        viewModelScope.launch {
            favoriteRepository.toggleFavorite(item)
            loadFavorites()
        }
    }

    fun removeFavorite(favoriteId: String) {
        viewModelScope.launch {
            favoriteRepository.removeFavorite(favoriteId)
            loadFavorites()
        }
    }

    fun addFavoriteToCart(item: FavoriteItem) {
        viewModelScope.launch {
            val cartItem = CartItem(
                coffeeId = item.coffeeId,
                name = item.name,
                price = item.price,
                imageName = "",
                quantity = 1
            )
            cartRepository.addToCart(cartItem)
        }
    }
}
