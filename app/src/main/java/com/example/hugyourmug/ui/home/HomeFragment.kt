package com.example.hugyourmug.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.hugyourmug.R
import com.example.hugyourmug.viewmodel.CoffeeViewModel

class HomeFragment : Fragment() {

    private val coffeeViewModel: CoffeeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val btnMyProfile = view.findViewById<Button>(R.id.btnMyProfile)
        btnMyProfile.setOnClickListener {
            findNavController().navigate(R.id.navigation_profile)
        }

        val btnMoodCoffee = view.findViewById<Button>(R.id.btnMoodCoffee)
        btnMoodCoffee.setOnClickListener {
            findNavController().navigate(R.id.navigation_mood)
        }

        observeViewModel()

        return view
    }

    private fun observeViewModel() {
        coffeeViewModel.allCoffees.observe(viewLifecycleOwner) {
        }
    }
}
