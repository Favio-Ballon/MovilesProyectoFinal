package com.example.movilesproyectofinal.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilesproyectofinal.models.dto.RestaurantCreateRequestDTO
import com.example.movilesproyectofinal.repositories.RestauranteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CrearRestauranteViewModel : ViewModel() {
    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _showLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _crearRestaurante: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val crearRestaurante: LiveData<Boolean> get() = _crearRestaurante

    fun crearRestaurante(name: String, description: String, address: String, city: String, token : String) {
        if(name.isEmpty() || description.isEmpty() || address.isEmpty() || city.isEmpty()){
            _errorMessage.postValue("Todos los campos son obligatorios")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            _showLoading.postValue(true)
            val restaurante = RestaurantCreateRequestDTO(name,address,city,description)
            RestauranteRepository.createRestaurant(
                restaurante,
                token,
                success = {
                    _showLoading.postValue(false)
                    _crearRestaurante.postValue(true)
                },
                failure = {
                    _errorMessage.postValue(it.message)
                    _showLoading.postValue(false)
                    it.printStackTrace()
                })

        }
    }

    fun editarRestaurante(id : Int,name: String, description: String, address: String, city: String, token : String){
        if(name.isEmpty() || description.isEmpty() || address.isEmpty() || city.isEmpty()){
            _errorMessage.postValue("Todos los campos son obligatorios")
            return
        }
        viewModelScope.launch(Dispatchers.IO) {
            _showLoading.postValue(true)
            val restaurante = RestaurantCreateRequestDTO(name,address,city,description)
            RestauranteRepository.editRestaurant(
                restaurante,
                token,
                id,
                success = {
                    _showLoading.postValue(false)
                    _crearRestaurante.postValue(true)
                },
                failure = {
                    _errorMessage.postValue(it.message)
                    _showLoading.postValue(false)
                    it.printStackTrace()
                })

        }
    }
}