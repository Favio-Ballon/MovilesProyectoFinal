package com.example.movilesproyectofinal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilesproyectofinal.models.Restaurante
import com.example.movilesproyectofinal.models.Restaurantes
import com.example.movilesproyectofinal.repositories.RestauranteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RestaurantesViewModel : ViewModel() {

    private val _restaurantes = MutableLiveData<Restaurantes>()
    val restaurantes: LiveData<Restaurantes> = _restaurantes


    fun fetchListaRestaurantes() {
        viewModelScope.launch(Dispatchers.IO) {

            RestauranteRepository.getRestaurantes(
                success = { restaurantes ->
                    restaurantes?.let {
                        _restaurantes.postValue(it)
                    }
                },
                failure = {
                    it.printStackTrace()
                })


        }
    }
}