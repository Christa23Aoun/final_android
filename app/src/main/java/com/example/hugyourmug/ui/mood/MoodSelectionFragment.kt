package com.example.hugyourmug.ui.mood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hugyourmug.R

class MoodSelectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_mood_selection, container, false)

        val recycler = view.findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerMoods)
        val moods = resources.getStringArray(R.array.moods_array).toList()

        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = MoodAdapter(moods) { mood ->
            val bundle = Bundle()
            bundle.putString("mood", mood)
            findNavController().navigate(R.id.navigation_mood_result, bundle)
        }

        return view
    }
}
