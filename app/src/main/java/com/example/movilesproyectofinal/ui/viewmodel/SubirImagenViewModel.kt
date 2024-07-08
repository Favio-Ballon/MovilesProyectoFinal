package com.example.movilesproyectofinal.ui.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movilesproyectofinal.repositories.RestauranteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SubirImagenViewModel : ViewModel() {
    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _showLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val showLoading: LiveData<Boolean> get() = _showLoading

    private val _subirImagen: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val subirImagen: LiveData<Boolean> get() = _subirImagen

    private val _selectedImageUri = MutableLiveData<Uri?>()
    val selectedImageUri: LiveData<Uri?> = _selectedImageUri

    private val _selectedImageBitmap = MutableLiveData<Bitmap?>()
    val selectedImageBitmap: LiveData<Bitmap?> = _selectedImageBitmap

    fun setImageUri(uri: Uri?) {
        _selectedImageUri.value = uri
    }

    fun setImageBitmap(bitmap: Bitmap?) {
        _selectedImageBitmap.value = bitmap
    }

    fun subirImagen(id: Long, isLogo: Boolean, imagen: Bitmap, token: String?) {
        if (token != null && token.isNotEmpty() && id > 0) {
            if (isLogo) {
                subirLogo(id, imagen, token)
            } else {
                subirGaleria(id, imagen, token)
            }
        }
    }

    fun subirLogo(id: Long, imagen: Bitmap, token: String) {

        viewModelScope.launch(Dispatchers.IO) {
            _showLoading.postValue(true)
            RestauranteRepository.uploadLogo(
                id.toInt(),
                imagen,
                token,
                success = {
                    _subirImagen.postValue(true)
                    _showLoading.postValue(false)
                },
                failure = {
                    _errorMessage.postValue(it.message)
                    _showLoading.postValue(false)
                    it.printStackTrace()
                })


        }
    }

    fun subirGaleria(id: Long, imagen: Bitmap, token: String) {

        viewModelScope.launch(Dispatchers.IO) {
            _showLoading.postValue(true)
            RestauranteRepository.uploadGallery(
                id.toInt(),
                imagen,
                token,
                success = {
                    _subirImagen.postValue(true)
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
