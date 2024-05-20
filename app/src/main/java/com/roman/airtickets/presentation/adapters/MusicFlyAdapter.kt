package com.roman.airtickets.presentation.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.roman.airtickets.R
import com.roman.airtickets.databinding.MusicFlyItemBinding
import com.roman.airtickets.entity.MusicFlay
import com.roman.airtickets.entity.OffersValue

class MusicFlyAdapter : Adapter<MusicFlyViewHolder>() {
    private var data: List<OffersValue> = emptyList()
    private val mapData = mutableMapOf<Int, Int>()
    fun setData(values: MusicFlay) {
        data = values.offers

        //setImages
        mapData[1] = R.drawable.image_1
        mapData[2] = R.drawable.image_2
        mapData[3] = R.drawable.image_3

        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicFlyViewHolder {
        val binding =
            MusicFlyItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MusicFlyViewHolder(binding)

    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: MusicFlyViewHolder, position: Int) {
        val musicFlyElement = data[position]
        val image = mapData.getOrDefault(musicFlyElement.id, R.drawable.default_image)
        Log.d("1111",image.toString())
        with(holder)
        {
            Glide.with(binding.imagePosterMusicFly)
                .load(image)
                .centerCrop()
                .into(binding.imagePosterMusicFly)
            val textMusicFlyName = musicFlyElement.title ?: let { "-" }
            val townText = musicFlyElement.town ?: let { "-" }
            val textFrom = itemView.context.getString(R.string.from_price)
            val textPrice = textFrom + " " + musicFlyElement.price.getValue()


            binding.musicFlyName.text = textMusicFlyName
            binding.musicFlyCity.text = townText
            binding.priceValueMusicFly.text = textPrice

        }

    }
}

class MusicFlyViewHolder(val binding: MusicFlyItemBinding) : ViewHolder(binding.root)