package com.example.movilesproyectofinal.models

import com.squareup.moshi.Json

typealias Menus = ArrayList<Menu>
class Menu (
    val id: Long,
    val name: String,

    @Json(name = "restaurant_id")
    val restaurantID: Long,

    val plates: List<Plate>
)