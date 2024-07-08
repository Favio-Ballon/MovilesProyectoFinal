package com.example.movilesproyectofinal.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilesproyectofinal.models.ReservaList
import com.example.movilesproyectofinal.repositories.RestauranteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MisReservasViewModel : ViewModel() {

    private val _reservas: MutableLiveData<ReservaList> by lazy {
        MutableLiveData<ReservaList>()
    }

    val reserva: LiveData<ReservaList> = _reservas

    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _showLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val showLoading: LiveData<Boolean> get() = _showLoading

    fun fetchReservas(token: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            _showLoading.postValue(true)
            RestauranteRepository.getReservas(
                token!!,
                success = { reservas ->
                    reservas?.let {
                        _reservas.postValue(it)
                    }
                    Log.d("RestaurantesViewModel", "fetchListaRestaurantes: ${reservas}")
                    _showLoading.postValue(false)
                },
                failure = {
                    _errorMessage.postValue(it.message)
                    _showLoading.postValue(false)
                    it.printStackTrace()
                })


        }
    }

    fun fetchReservasByRestaurante(token: String?, idRestaurante: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _showLoading.postValue(true)
            RestauranteRepository.getReservasByRestaurante(
                token!!,
                idRestaurante,
                success = { reservas ->
                    reservas?.let {
                        _reservas.postValue(it)
                    }
                    Log.d("RestaurantesViewModel", "fetchListaRestaurantes: ${reservas}")
                    _showLoading.postValue(false)
                },
                failure = {
                    _errorMessage.postValue(it.message)
                    _showLoading.postValue(false)
                    it.printStackTrace()
                })
        }
    }
}