package com.example.movilesproyectofinal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegistrarseViewModel : ViewModel() {

    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _showLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val showLoading: LiveData<Boolean> get() = _showLoading

    fun registrarse(){
        //TODO: Implementar la logica de registro
    }

}