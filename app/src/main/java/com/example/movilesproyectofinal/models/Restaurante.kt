package com.example.movilesproyectofinal.models

import com.squareup.moshi.Json


data class Restaurante(
    val id: Long,
    val name: String,
    val address: String,
    val city: String,
    val description: String,

    @Json(name = "user_id")
    val userID: Long,

    val logo: String,
    val owner: Owner
)
