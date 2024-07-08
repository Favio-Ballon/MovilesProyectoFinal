package com.example.movilesproyectofinal.models

import com.squareup.moshi.Json

typealias FoodList = List<Food>
data class Food (
    val plate_id: Long,
    var qty: String
)

