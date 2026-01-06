package com.example.hugyourmug.ui.mood

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.hugyourmug.R

class MoodAdapter(
    private val moods: List<String>,
    private val onClick: (String) -> Unit
) : RecyclerView.Adapter<MoodAdapter.MoodViewHolder>() {

    inner class MoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtMood: TextView = itemView.findViewById(R.id.txtMood)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoodViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_mood, parent, false)
        return MoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoodViewHolder, position: Int) {
        val mood = moods[position]
        holder.txtMood.text = mood.replace("_", " ").uppercase()
        holder.itemView.setOnClickListener { onClick(mood) }
    }

    override fun getItemCount(): Int = moods.size
}
