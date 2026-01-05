package com.example.hugyourmug.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hugyourmug.databinding.FragmentFavoritesBinding
import com.example.hugyourmug.viewmodel.FavoritesViewModel

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerFavorites.layoutManager = LinearLayoutManager(requireContext())

        val adapter = FavoritesAdapter(
            onRemove = { item ->
                viewModel.removeFavorite(item.id)
            },
            onAddToCart = { item ->
                viewModel.addFavoriteToCart(item)
            }
        )

        binding.recyclerFavorites.adapter = adapter

        viewModel.favorites.observe(viewLifecycleOwner) { items ->
            adapter.updateList(items)
        }

        viewModel.loadFavorites()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
