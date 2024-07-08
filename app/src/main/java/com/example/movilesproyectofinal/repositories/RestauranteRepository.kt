package com.example.movilesproyectofinal.repositories

import android.util.Log
import com.example.movilesproyectofinal.api.APIProyecto
import com.example.movilesproyectofinal.models.Menus
import com.example.movilesproyectofinal.models.ReservaList
import com.example.movilesproyectofinal.models.Restaurante
import com.example.movilesproyectofinal.models.Restaurantes
import com.example.movilesproyectofinal.models.dto.ReservacionRequestDTO
import com.example.movilesproyectofinal.models.dto.ReservacionResponseDTO
import com.example.movilesproyectofinal.models.dto.RestauranteFiltroDTO
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

    fun getRestaurantesFiltered(filter: RestauranteFiltroDTO, success: (Restaurantes?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getRetrofitInstance()

        val service: APIProyecto =
            retrofit.create(APIProyecto::class.java)

        service.getRestaurantesFiltered(filter).enqueue(object : Callback<Restaurantes> {
            override fun onResponse(call: Call<Restaurantes>, response: Response<Restaurantes>) {
                success(response.body())
            }

            override fun onFailure(call: Call<Restaurantes>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun makeReservation(reservation: ReservacionRequestDTO, token: String, success: (ReservacionResponseDTO?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)

        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        service.makeReservation(reservation).enqueue(object : Callback<ReservacionResponseDTO> {
            override fun onResponse(call: Call<ReservacionResponseDTO>, response: Response<ReservacionResponseDTO>) {
                if (response.isSuccessful) {
                    Log.d("Response", response.body().toString())
                    success(response.body())
                } else {
                    Log.e("Error", "Error code: ${response.code()}, Error message: ${response.message()}")
                    failure(Exception("Error code: ${response.code()}, Error message: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<ReservacionResponseDTO>, t: Throwable) {
                Log.e("Failure", "Request failed", t)
                failure(t)
            }
        })
    }

    fun getReservas(token: String, success: (ReservaList?) -> Unit, failure: (Throwable) -> Unit) {
        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)

        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        service.getReservas().enqueue(object : Callback<ReservaList> {
            override fun onResponse(call: Call<ReservaList>, response: Response<ReservaList>) {
                if (response.isSuccessful) {
                    success(response.body())
                } else {
                    Log.e("Error", "Error code: ${response.code()}, Error message: ${response.message()}")
                    failure(Exception("Error code: ${response.code()}, Error message: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<ReservaList>, t: Throwable) {
                Log.e("Failure", "Request failed", t)
                failure(t)
            }
        })
    }
}