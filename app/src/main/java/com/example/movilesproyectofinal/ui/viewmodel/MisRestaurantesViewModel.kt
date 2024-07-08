package com.example.movilesproyectofinal.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilesproyectofinal.models.Restaurantes
import com.example.movilesproyectofinal.repositories.RestauranteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MisRestaurantesViewModel : ViewModel() {

    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _showLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _restaurantes: MutableLiveData<Restaurantes> by lazy {
        MutableLiveData<Restaurantes>(Restaurantes())
    }

    val restaurantes: LiveData<Restaurantes> = _restaurantes

    private val _deleteSuccess: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val deleteSuccess: LiveData<Boolean> get() = _deleteSuccess

    fun fetchListaRestaurantes(token : String) {
        viewModelScope.launch(Dispatchers.IO) {
            _showLoading.postValue(true)
            RestauranteRepository.getRestaurantesByUsuario(
                token,
                success = { restaurantes ->
                    restaurantes?.let {
                        _restaurantes.postValue(ArrayList(it))
                    }
                    Log.d("RestaurantesViewModel", "fetchListaRestaurantes: ${restaurantes}")
                    _showLoading.postValue(false)
                },
                failure = {
                    _errorMessage.postValue(it.message)
                    _showLoading.postValue(false)
                    it.printStackTrace()
                })


        }
    }

    fun deleteRestaurante(token: String, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _showLoading.postValue(true)
            RestauranteRepository.deleteRestaurante(
                token,
                id,
                success = {
                    fetchListaRestaurantes(token)
                    _showLoading.postValue(false)
                    _deleteSuccess.postValue(true)
                },
                failure = {
                    _errorMessage.postValue(it.message)
                    _showLoading.postValue(false)
                    it.printStackTrace()
                })
        }
    }

}