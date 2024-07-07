package com.example.movilesproyectofinal.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilesproyectofinal.ui.fragment.RegisterFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegistrarseViewModel : ViewModel() {

    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _showLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _goToLogin: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val goToLogin: LiveData<Boolean> get() = _goToLogin

    fun registrarse(
        username: String,
        email: String, password: String, telefono: String
    ){
        viewModelScope.launch(Dispatchers.IO){
            _showLoading.postValue(true)
            UserRepository.doRegister(
                username,
                email,
                password,
                telefono,
                success = {
                    if (it == null){
                        _errorMessage.postValue("Error al registrar usuario")
                        _showLoading.postValue(false)
                        return@doRegister
                    }
                    _errorMessage.postValue("")
                    _showLoading.postValue(false)
                    _goToLogin.postValue(true)
                },
                failure = {
                    it.printStackTrace()
                    _showLoading.postValue(false)
                }
            )
        }
    }

}