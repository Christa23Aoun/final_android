package com.example.hugyourmug.ui.mood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hugyourmug.R
import com.example.hugyourmug.databinding.FragmentMoodResultBinding
import com.example.hugyourmug.data.model.CartItem
import com.example.hugyourmug.data.model.FavoriteItem
import com.example.hugyourmug.data.repository.CartRepository
import com.example.hugyourmug.data.repository.FavoriteRepository
import com.example.hugyourmug.ui.menu.CoffeeMenuAdapter
import com.example.hugyourmug.viewmodel.CoffeeViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MoodResultFragment : Fragment() {

    private var _binding: FragmentMoodResultBinding? = null
    private val binding get() = _binding!!

    private val coffeeViewModel: CoffeeViewModel by viewModels()

    private val cartRepository = CartRepository()
    private val favoriteRepository = FavoriteRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoodResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mood = arguments?.getString("mood") ?: return

        binding.txtMoodTitle.text =
            "Perfect coffees for when you're feeling $mood ☕"

        val adapter = CoffeeMenuAdapter(
            items = emptyList(),

            onAddClick = { coffee ->
                val cartItem = CartItem(
                    id = "",
                    coffeeId = coffee.id,
                    name = coffee.name,
                    price = coffee.smallPrice,
                    quantity = 1
                )

                CoroutineScope(Dispatchers.IO).launch {
                    cartRepository.addToCart(cartItem)
                }

                Toast.makeText(
                    requireContext(),
                    "${coffee.name} added to cart ☕",
                    Toast.LENGTH_SHORT
                ).show()
            },

            onFavoriteClick = { coffee ->
                val favoriteItem = FavoriteItem(
                    id = "",
                    coffeeId = coffee.id,
                    name = coffee.name,
                    imageName = coffee.imageName
                )

                CoroutineScope(Dispatchers.IO).launch {
                    favoriteRepository.toggleFavorite(favoriteItem)
                }

                Toast.makeText(
                    requireContext(),
                    "${coffee.name} added to favorites ❤️",
                    Toast.LENGTH_SHORT
                ).show()
            }
        )

        binding.recyclerMoodSuggestions.layoutManager =
            LinearLayoutManager(requireContext())

        binding.recyclerMoodSuggestions.adapter = adapter

        coffeeViewModel.suggestedCoffees.observe(viewLifecycleOwner) {
            adapter.updateData(it)
        }

        coffeeViewModel.loadSuggestedCoffees(mood)

        binding.btnChangeMood.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnViewMenu.setOnClickListener {
            findNavController().navigate(R.id.navigation_menu)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
