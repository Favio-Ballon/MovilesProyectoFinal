package com.example.movilesproyectofinal.api

import com.example.movilesproyectofinal.models.Menu
import com.example.movilesproyectofinal.models.Menus
import com.example.movilesproyectofinal.models.Restaurante
import com.example.movilesproyectofinal.models.Restaurantes
import com.example.movilesproyectofinal.models.dto.LoginRequestDTO
import com.example.movilesproyectofinal.models.dto.LoginResponseDTO
import com.example.movilesproyectofinal.models.dto.RegisterRequestDTO
import com.example.movilesproyectofinal.models.dto.RestauranteFiltroDTO
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

    @GET("restaurants/{id}/menu")
    fun getMenuById(
        @Path("id") id: Int
    ): Call<Menus>

    @POST("restaurants/search")
    fun getRestaurantesFiltered(
       @Body filter: RestauranteFiltroDTO
    ): Call<Restaurantes>

}