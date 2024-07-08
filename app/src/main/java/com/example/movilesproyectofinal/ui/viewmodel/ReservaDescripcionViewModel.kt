package com.example.movilesproyectofinal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilesproyectofinal.repositories.RestauranteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReservaDescripcionViewModel : ViewModel() {

    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _showLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _cancelado: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }

    val cancelado: LiveData<Boolean> get() = _cancelado

    fun cancelarReserva(id : Long,token: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _showLoading.postValue(true)
            RestauranteRepository.cancelReservation(
                id,
                token,
                success = {
                    _cancelado.postValue(true)
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