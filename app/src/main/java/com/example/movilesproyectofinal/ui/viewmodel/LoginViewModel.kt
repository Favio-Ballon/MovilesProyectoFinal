package com.example.movilesproyectofinal.ui.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movilesproyectofinal.repositories.PreferencesRepository

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {

    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _showLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val showLoading: LiveData<Boolean> get() = _showLoading


    val _goToRestaurantes: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val goToRestaurantes: LiveData<Boolean> get() = _goToRestaurantes

    fun login(email: String, password: String, context: Context) {

        viewModelScope.launch(Dispatchers.IO) {
            _showLoading.postValue(true)
            UserRepository.doLogin(email,
                password,
                success = {
                    if (it == null) {
                        _errorMessage.value = "Usuario o contraseña incorrectos"
                        _showLoading.postValue(false)
                        return@doLogin
                    }
                    _errorMessage.value = ""
                    Log.d("MainViewModel", "Token: ${it.access_token}")
                    val token: String = it.access_token!!
                    PreferencesRepository.saveToken(token, context)
                    Toast.makeText(context, "Bienvenido", Toast.LENGTH_SHORT).show()
                    _showLoading.postValue(false)
                    _goToRestaurantes.postValue(true)
                }, failure = {
                    it.printStackTrace()
                    _showLoading.postValue(false)
                })
        }
    }



}