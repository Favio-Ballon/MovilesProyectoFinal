package com.example.movilesproyectofinal.models

import com.squareup.moshi.Json
import java.io.Serializable

typealias ReservaList = List<Reserva>
class Reserva (
    val id: Long,

    val user_id: Long,


    val restaurant_id: Long,

    val date: String,
    val time: String,
    val people: Long,
    val status: String,


    val created_at: String,

    val updated_at: String,

    val user: User,
    val restaurant: Restaurante
) : Serializable