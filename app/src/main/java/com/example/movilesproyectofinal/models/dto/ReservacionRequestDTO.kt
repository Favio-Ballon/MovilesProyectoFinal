package com.example.movilesproyectofinal.models.dto

import com.example.movilesproyectofinal.models.Food
import com.squareup.moshi.Json

data class ReservacionRequestDTO(
    val restaurant_id: Long,
    val date: String,
    val time: String,
    val people: Long,
    val food: List<Food>? = null
)


