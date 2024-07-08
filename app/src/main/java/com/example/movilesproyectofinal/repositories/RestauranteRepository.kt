package com.example.movilesproyectofinal.repositories

import android.graphics.Bitmap
import android.util.Log
import com.example.movilesproyectofinal.api.APIProyecto
import com.example.movilesproyectofinal.models.Menus
import com.example.movilesproyectofinal.models.Reserva
import com.example.movilesproyectofinal.models.ReservaList
import com.example.movilesproyectofinal.models.Restaurante
import com.example.movilesproyectofinal.models.Restaurantes
import com.example.movilesproyectofinal.models.dto.ReservacionRequestDTO
import com.example.movilesproyectofinal.models.dto.ReservacionResponseDTO
import com.example.movilesproyectofinal.models.dto.RestaurantCreateRequestDTO
import com.example.movilesproyectofinal.models.dto.RestauranteFiltroDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import okhttp3.MediaType.Companion.toMediaTypeOrNull

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

    fun getRestaurantesFiltered(
        filter: RestauranteFiltroDTO,
        success: (Restaurantes?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
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

    fun makeReservation(
        reservation: ReservacionRequestDTO,
        token: String,
        success: (ReservacionResponseDTO?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)

        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        service.makeReservation(reservation).enqueue(object : Callback<ReservacionResponseDTO> {
            override fun onResponse(
                call: Call<ReservacionResponseDTO>,
                response: Response<ReservacionResponseDTO>
            ) {
                if (response.isSuccessful) {
                    Log.d("Response", response.body().toString())
                    success(response.body())
                } else {
                    Log.e(
                        "Error",
                        "Error code: ${response.code()}, Error message: ${response.message()}"
                    )
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
                    Log.e(
                        "Error",
                        "Error code: ${response.code()}, Error message: ${response.message()}"
                    )
                    failure(Exception("Error code: ${response.code()}, Error message: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<ReservaList>, t: Throwable) {
                Log.e("Failure", "Request failed", t)
                failure(t)
            }
        })
    }

    fun cancelReservation(
        id: Long,
        token: String,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)

        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        service.cancelReservation(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    Log.e(
                        "Error",
                        "Error code: ${response.code()}, Error message: ${response.message()}"
                    )
                    failure(Exception("Error code: ${response.code()}, Error message: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Failure", "Request failed", t)
                failure(t)
            }
        })
    }

    fun createRestaurant(
        restaurant: RestaurantCreateRequestDTO,
        token: String,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)

        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        service.createRestaurant(restaurant).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    Log.e(
                        "Error",
                        "Error code: ${response.code()}, Error message: ${response.message()}"
                    )
                    failure(Exception("Error code: ${response.code()}, Error message: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Failure", "Request failed", t)
                failure(t)
            }
        })
    }

    fun getRestaurantesByUsuario(
        token: String,
        success: (Restaurantes?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)

        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        service.getRestauranterByUsuario().enqueue(object : Callback<Restaurantes> {
            override fun onResponse(call: Call<Restaurantes>, response: Response<Restaurantes>) {
                success(response.body())
            }

            override fun onFailure(call: Call<Restaurantes>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun deleteRestaurante(
        token: String,
        id: Int,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)

        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        service.deleteRestaurante(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    Log.e(
                        "Error",
                        "Error code: ${response.code()}, Error message: ${response.message()}"
                    )
                    failure(Exception("Error code: ${response.code()}, Error message: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Failure", "Request failed", t)
                failure(t)
            }
        })
    }

    fun editRestaurant(
        restaurant: RestaurantCreateRequestDTO,
        token: String,
        id: Int,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)

        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        service.editRestaurante(id, restaurant).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    Log.e(
                        "Error",
                        "Error code: ${response.code()}, Error message: ${response.message()}"
                    )
                    failure(Exception("Error code: ${response.code()}, Error message: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Failure", "Request failed", t)
                failure(t)
            }
        })
    }

    fun uploadLogo(
        id: Int,
        bitmap: Bitmap,
        token: String,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        // Convert Bitmap to ByteArray
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val bitmapData = bos.toByteArray()


        // Create RequestBody and MultipartBody.Part
        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), bitmapData)
        val body = MultipartBody.Part.createFormData("image", "logo.jpg", requestFile)

        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        service.uploadLogo(id, body).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                    Log.d("Response", response.body().toString())
                } else {
                    Log.e(
                        "Error",
                        "Error code: ${response.code()}, Error message: ${response.message()}"
                    )
                    failure(Exception("Error code: ${response.code()}, Error message: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Failure", "Request failed", t)
                failure(t)
            }
        })
    }

    fun uploadGallery(
        id: Int,
        bitmap: Bitmap,
        token: String,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        // Convert Bitmap to ByteArray
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos)
        val bitmapData = bos.toByteArray()
        // Create RequestBody and MultipartBody.Part
        val requestFile = RequestBody.create("image/jpeg".toMediaTypeOrNull(), bitmapData)
        val body = MultipartBody.Part.createFormData("image", "gallery.jpg", requestFile)
        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)
        val service: APIProyecto = retrofit.create(APIProyecto::class.java)
        service.uploadGallery(id, body).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                    Log.d("Response", response.body().toString())
                } else {
                    Log.e(
                        "Error",
                        "Error code: ${response.code()}, Error message: ${response.message()}"
                    )
                    failure(Exception("Error code: ${response.code()}, Error message: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Failure", "Request failed", t)
                failure(t)
            }
        })


    }

    fun getReservasByRestaurante(
        token: String,
        idRestaurante: Int,
        success: (ReservaList?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)

        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        service.getReservasByRestaurante(idRestaurante).enqueue(object : Callback<ReservaList> {
            override fun onResponse(call: Call<ReservaList>, response: Response<ReservaList>) {
                success(response.body())
            }

            override fun onFailure(call: Call<ReservaList>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun getReservasById(
        token: String,
        id: Int,
        success: (Reserva?) -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)

        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        service.getReservaById(id).enqueue(object : Callback<Reserva> {
            override fun onResponse(call: Call<Reserva>, response: Response<Reserva>) {
                success(response.body())
            }

            override fun onFailure(call: Call<Reserva>, t: Throwable) {
                failure(t)
            }
        })
    }

    fun confirmReservation(
        token: String,
        id: Long,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)

        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        service.confirmReservation(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    Log.e(
                        "Error",
                        "Error code: ${response.code()}, Error message: ${response.message()}"
                    )
                    failure(Exception("Error code: ${response.code()}, Error message: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Failure", "Request failed", t)
                failure(t)
            }
        })
    }

    fun deleteMenu(
        token: String,
        id: Int,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)

        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        service.deleteMenu(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    Log.e(
                        "Error",
                        "Error code: ${response.code()}, Error message: ${response.message()}"
                    )
                    failure(Exception("Error code: ${response.code()}, Error message: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Failure", "Request failed", t)
                failure(t)
            }
        })
    }

    fun deletePlate(
        token: String,
        id: Int,
        success: () -> Unit,
        failure: (Throwable) -> Unit
    ) {
        val retrofit = RetrofitRepository.getReotrofitInstanceWithToken(token)

        val service: APIProyecto = retrofit.create(APIProyecto::class.java)

        service.deletePlate(id).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    success()
                } else {
                    Log.e(
                        "Error",
                        "Error code: ${response.code()}, Error message: ${response.message()}"
                    )
                    failure(Exception("Error code: ${response.code()}, Error message: ${response.message()}"))
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e("Failure", "Request failed", t)
                failure(t)
            }
        })
    }
}