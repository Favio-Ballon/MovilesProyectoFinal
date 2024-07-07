package com.example.movilesproyectofinal.api

import com.example.movilesproyectofinal.models.Restaurante
import com.example.movilesproyectofinal.models.Restaurantes
import com.example.movilesproyectofinal.models.dto.LoginRequestDTO
import com.example.movilesproyectofinal.models.dto.LoginResponseDTO
import com.example.movilesproyectofinal.models.dto.RegisterRequestDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIProyecto {
    @POST("loginuser")
    fun login(@Body loginRequest: LoginRequestDTO): Call<LoginResponseDTO>

    @POST("registeruser")
    fun register(@Body loginRequest: RegisterRequestDTO): Call<LoginResponseDTO>

    @POST("restaurants/search")
    fun getRestaurantes(): Call<Restaurantes>

    @GET("restaurants/{id}")
    fun getRestauranteById(
        @Path("id") id: Int
    ): Call<Restaurante>
}