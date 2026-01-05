package com.example.hugyourmug.ui.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hugyourmug.R
import com.example.hugyourmug.data.model.CartItem
import com.example.hugyourmug.data.model.Coffee
import com.example.hugyourmug.data.model.FavoriteItem
import com.example.hugyourmug.viewmodel.CartViewModel
import com.example.hugyourmug.viewmodel.CoffeeViewModel
import com.example.hugyourmug.viewmodel.FavoritesViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class MenuFragment : Fragment() {

    private lateinit var recyclerMenu: RecyclerView
    private lateinit var adapter: CoffeeMenuAdapter

    private val coffeeViewModel: CoffeeViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private val favoritesViewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerMenu = view.findViewById(R.id.recyclerMenu)
        recyclerMenu.layoutManager = LinearLayoutManager(requireContext())

        adapter = CoffeeMenuAdapter(
            items = emptyList(),
            onAddClick = { coffee ->
                addToCart(coffee)
            },
            onFavoriteClick = { coffee ->
                toggleFavorite(coffee)
            }
        )

        recyclerMenu.adapter = adapter

        coffeeViewModel.allCoffees.observe(viewLifecycleOwner) { coffees ->
            adapter.updateData(coffees)
        }
    }

    private fun addToCart(coffee: Coffee) {
        val item = CartItem(
            coffeeId = coffee.id,
            name = coffee.name,
            price = coffee.smallPrice,
            imageName = coffee.imageName,
            quantity = 1
        )
        cartViewModel.addToCart(item)
        Snackbar.make(requireView(), "${coffee.name} added to cart", Snackbar.LENGTH_SHORT).show()
    }

    private fun toggleFavorite(coffee: Coffee) {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        val item = FavoriteItem(
            userId = userId,
            coffeeId = coffee.id,
            name = coffee.name,
            price = coffee.smallPrice,
            imageName = coffee.imageName
        )

        favoritesViewModel.toggleFavorite(item)
        Snackbar.make(requireView(), "Favorite updated", Snackbar.LENGTH_SHORT).show()
    }

}
