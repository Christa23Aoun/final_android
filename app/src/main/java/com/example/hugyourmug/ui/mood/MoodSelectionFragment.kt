package com.example.hugyourmug.ui.mood

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.hugyourmug.R

class MoodSelectionFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_mood_selection, container, false)

        view.findViewById<View>(R.id.moodHappy).setOnClickListener { navigate("happy") }
        view.findViewById<View>(R.id.moodTired).setOnClickListener { navigate("tired") }
        view.findViewById<View>(R.id.moodStudy).setOnClickListener { navigate("study") }
        view.findViewById<View>(R.id.moodRelax).setOnClickListener { navigate("relaxed") }

        return view
    }

    private fun navigate(mood: String) {
        val bundle = Bundle()
        bundle.putString("mood", mood)
        findNavController().navigate(R.id.navigation_mood_result, bundle)
    }
}
