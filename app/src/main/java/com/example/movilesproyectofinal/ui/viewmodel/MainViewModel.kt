package com.example.movilesproyectofinal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class MainViewModel : ViewModel() {
    private val _login : MutableLiveData<Boolean> by lazy {
        MutableLiveData <Boolean>(false)
    }

    val login: LiveData<Boolean> get() = _login

    private val _register : MutableLiveData<Boolean> by lazy {
        MutableLiveData <Boolean>(false)
    }

    val register: LiveData<Boolean> get() = _register

    private val _visitante : MutableLiveData<Boolean> by lazy {
        MutableLiveData <Boolean>(false)
    }

    val visitante: LiveData<Boolean> get() = _visitante

    fun goToLogin(){
        _login.value = true
    }

    fun goToRegistrarse(){
        _register.value = true
    }

    fun goToVisitante(){
        _visitante.value = true
    }



}