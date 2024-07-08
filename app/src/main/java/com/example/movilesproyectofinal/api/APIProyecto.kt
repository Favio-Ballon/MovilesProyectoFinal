package com.example.movilesproyectofinal.api

import com.example.movilesproyectofinal.models.Menu
import com.example.movilesproyectofinal.models.Menus
import com.example.movilesproyectofinal.models.Reserva
import com.example.movilesproyectofinal.models.ReservaList
import com.example.movilesproyectofinal.models.Restaurante
import com.example.movilesproyectofinal.models.Restaurantes
import com.example.movilesproyectofinal.models.dto.LoginRequestDTO
import com.example.movilesproyectofinal.models.dto.LoginResponseDTO
import com.example.movilesproyectofinal.models.dto.RegisterRequestDTO
import com.example.movilesproyectofinal.models.dto.ReservacionRequestDTO
import com.example.movilesproyectofinal.models.dto.ReservacionResponseDTO
import com.example.movilesproyectofinal.models.dto.RestaurantCreateRequestDTO
import com.example.movilesproyectofinal.models.dto.RestauranteFiltroDTO
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
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

    @GET("restaurants")
    fun getRestauranterByUsuario(): Call<Restaurantes>

    @DELETE("restaurants/{id}")
    fun deleteRestaurante(
        @Path("id") id: Int
    ): Call<Void>


    @PUT("restaurants/{id}")
    fun editRestaurante(
        @Path("id") id: Int,
        @Body restaurante: RestaurantCreateRequestDTO
    ): Call<Void>


    @Multipart
    @POST("restaurants/{id}/logo")
    fun uploadLogo(
        @Path("id") id: Int,
        @Part logo: MultipartBody.Part
    ): Call<Void>

    @Multipart
    @POST("restaurants/{id}/photo")
    fun uploadGallery(
        @Path("id") id: Int,
        @Part gallery: MultipartBody.Part
    ): Call<Void>

    @POST("restaurants/search")
    fun getRestaurantesFiltered(
       @Body filter: RestauranteFiltroDTO
    ): Call<Restaurantes>

    @POST("restaurants")
    fun createRestaurant(
        @Body restaurant: RestaurantCreateRequestDTO
    ): Call<Void>

    @POST("reservations")
    fun makeReservation(
        @Body reservation: ReservacionRequestDTO
    ): Call<ReservacionResponseDTO>

    @GET("reservations")
    fun getReservas(): Call<ReservaList>

    @POST("reservations/{id}/cancel")
    fun cancelReservation(
        @Path("id") id: Long
    ): Call<Void>

    @POST("reservations/{id}/confirm")
    fun confirmReservation(
        @Path("id") id: Long
    ): Call<Void>


    @GET("restaurants/{id}/reservations")
    fun getReservasByRestaurante(
        @Path("id") id: Int
    ): Call<ReservaList>

    @GET("reservations/{id}")
    fun getReservaById(
        @Path("id") id: Int
    ): Call<Reserva>


    @DELETE("plates/{id}")
    fun deletePlate(
        @Path("id") id: Int
    ): Call<Void>

    @DELETE("menu-categories/{id}")
    fun deleteMenu(
        @Path("id") id: Int
    ):Call<Void>

}