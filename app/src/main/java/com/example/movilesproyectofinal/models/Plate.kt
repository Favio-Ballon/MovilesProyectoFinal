package com.example.movilesproyectofinal.models

import com.squareup.moshi.Json

data class Plate (
    val id: Long,
    val name: String,
    val description: String,
    val price: String,

    @Json(name = "menu_category_id")
    val menuCategoryID: Long
)