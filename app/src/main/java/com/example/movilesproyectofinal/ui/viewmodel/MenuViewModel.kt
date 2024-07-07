package com.example.movilesproyectofinal.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilesproyectofinal.models.Menus
import com.example.movilesproyectofinal.repositories.RestauranteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel : ViewModel() {
    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _showLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _menus: MutableLiveData<Menus> by lazy {
        MutableLiveData<Menus>(Menus())
        }
    val menus: LiveData<Menus> = _menus

    fun fetchListaMenus(id : Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _showLoading.postValue(true)
            RestauranteRepository.getMenuById(
                id,
                success = { Menus ->
                    Menus?.let {
                        _menus.postValue(ArrayList(it))
                    }
                    Log.d("MenuViewModel", "fetchListaMenus: ${Menus}")
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