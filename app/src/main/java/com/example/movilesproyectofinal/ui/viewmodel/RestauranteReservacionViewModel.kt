package com.example.movilesproyectofinal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilesproyectofinal.models.Food
import com.example.movilesproyectofinal.models.FoodList
import com.example.movilesproyectofinal.models.dto.ReservacionRequestDTO
import com.example.movilesproyectofinal.repositories.PreferencesRepository
import com.example.movilesproyectofinal.repositories.RestauranteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RestauranteReservacionViewModel : ViewModel() {

    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _showLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _reservacion: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val reservacion: LiveData<Boolean> get() = _reservacion


    fun reservarRestaurante(idRestaurante: Long?, fecha: String, hora: String, personas: Long?,food : ArrayList<Food>? ,token : String) {

        if (validarDatos(fecha, hora, personas)) {

            val reservacionRequestDTO = ReservacionRequestDTO(
                idRestaurante!!, fecha, hora,
                personas!!, food
            )
            viewModelScope.launch(Dispatchers.IO) {
                _showLoading.postValue(true)
                RestauranteRepository.makeReservation(
                    reservacionRequestDTO,
                    token,
                    success = {
                        if (it == null) {
                            _errorMessage.postValue("Error al reservar")
                            _showLoading.postValue(false)
                            return@makeReservation
                        }

                        _reservacion.postValue(true)
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

    fun validarDatos(fecha: String, hora: String, personas: Long?): Boolean {
        if (fecha.isEmpty() || hora.isEmpty() || personas == null) {
            _errorMessage.postValue("Por favor llene todos los campos")
            return false
        } else if (personas <= 0) {
            _errorMessage.postValue("La cantidad de personas debe ser mayor a 0")
            return false
        } else if (!isValidDateFormat(fecha)) {
            _errorMessage.postValue("La fecha debe tener el formato aaaa-mm-dd")
            return false
        } else if (!isValidTimeFormat(hora)) {
            _errorMessage.postValue("La hora debe tener el formato hh:mm")
            return false
        }

        return true
    }

    fun isValidTimeFormat(hora: String): Boolean {
        val pattern = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$".toRegex()
        return pattern.matches(hora)
    }

    fun isValidDateFormat(fecha: String): Boolean {
        val pattern = "^\\d{4}-\\d{2}-\\d{2}$".toRegex()
        return pattern.matches(fecha)
    }

}