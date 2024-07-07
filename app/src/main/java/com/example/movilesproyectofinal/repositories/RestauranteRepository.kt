package com.example.movilesproyectofinal.repositories

import com.example.movilesproyectofinal.api.APIProyecto
import com.example.movilesproyectofinal.models.Menus
import com.example.movilesproyectofinal.models.Restaurante
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

    fun getRestauranteById(id: Int, success: (Restaurante?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIProyecto =
            retrofit.create(APIProyecto::class.java)

        service.getRestauranteById(id).enqueue(object : Callback<Restaurante?> {
            override fun onResponse(call: Call<Restaurante?>, response: Response<Restaurante?>) {
                success(response.body())
            }

            override fun onFailure(call: Call<Restaurante?>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getMenuById(id: Int, success: (Menus?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIProyecto =
            retrofit.create(APIProyecto::class.java)

        service.getMenuById(id).enqueue(object : Callback<Menus> {
            override fun onResponse(call: Call<Menus>, response: Response<Menus>) {
                success(response.body())
            }

            override fun onFailure(call: Call<Menus>, t: Throwable) {
                failure(t)
            }
        })
    }
}