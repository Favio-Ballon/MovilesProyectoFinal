package com.example.movilesproyectofinal.repositories

import com.example.movilesproyectofinal.api.APIProyecto
import com.example.movilesproyectofinal.models.Restaurantes
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object RestauranteRepository {
    fun getRestaurantes(success: (Restaurantes?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIProyecto =
            retrofit.create(APIProyecto::class.java)

        service.getRestaurantes().enqueue(object : Callback<Restaurantes> {
            override fun onResponse(res: Call<Restaurantes>, response: Response<Restaurantes>) {
                val postList = response.body()
                success(postList)
            }

            override fun onFailure(res: Call<Restaurantes>, t: Throwable) {
                failure(t)
            }
        })
    }
}