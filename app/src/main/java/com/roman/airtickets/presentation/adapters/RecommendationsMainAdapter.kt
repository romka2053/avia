package com.roman.airtickets.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.roman.airtickets.databinding.RecommendationsItemBinding
import com.roman.airtickets.entity.Recommendations

class RecommendationsMainAdapter(private val onclick: (String) -> Unit) :
    Adapter<RecommendationsViewHolder>() {


    private val data = Recommendations.recommendations
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendationsViewHolder {
        val binding =
            RecommendationsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendationsViewHolder(binding)

    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: RecommendationsViewHolder, position: Int) {
        with(holder)
        {
            binding.posterRecommendation.setImageResource(data[position].idImage)
            binding.popularName.text = data[position].name
            binding.descriptionText.text = data[position].description

            binding.root.setOnClickListener {
                onclick(data[position].name)
            }
        }
    }

}

class RecommendationsViewHolder(val binding: RecommendationsItemBinding) : ViewHolder(binding.root)