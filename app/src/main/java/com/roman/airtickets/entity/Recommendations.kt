package com.roman.airtickets.entity

import com.roman.airtickets.R

data class Recommendations(
    val id: Int,
    val idImage: Int,
    val name: String,
    val description: String = "Популярное направление"
) {
    companion object {
        val recommendations = listOf(
            Recommendations(1, R.drawable.stambul, "Стамбул"),
            Recommendations(1, R.drawable.sochi, "Сочи"),
            Recommendations(1, R.drawable.phuket, "Пхукет"),
        )
    }
}