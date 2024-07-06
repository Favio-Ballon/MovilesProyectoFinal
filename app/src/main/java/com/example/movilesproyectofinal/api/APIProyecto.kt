package com.example.movilesproyectofinal.api

import com.example.movilesproyectofinal.models.dto.LoginRequestDTO
import com.example.movilesproyectofinal.models.dto.LoginResponseDTO
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface APIProyecto {
    @POST("loginuser")
    fun login(@Body loginRequest: LoginRequestDTO): Call<LoginResponseDTO>


}