package com.example.movilesproyectofinal.ui.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FullScreenImageViewModel : ViewModel() {
    private val _errorMessage: MutableLiveData<String> by lazy {
        MutableLiveData<String>("")
    }
    val errorMessage: LiveData<String> get() = _errorMessage

    private val _showLoading: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val showLoading: LiveData<Boolean> get() = _showLoading


    private val _closeActivity : MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>(false)
    }
    val closeActivity : LiveData<Boolean> get() = _closeActivity



    fun closeActivity() {
        _closeActivity.postValue(true)
    }

    fun loadImage(url: String, context : Context, onSuccess: (Bitmap) -> Unit, onError: (Exception) -> Unit) {

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _showLoading.postValue(true)
                val bitmap = Glide.with(context)
                    .asBitmap()
                    .load(url)
                    .submit()
                    .get()

                withContext(Dispatchers.Main) {
                    onSuccess(bitmap)
                    _showLoading.postValue(false)
                }
            } catch (e: Exception) {
                _errorMessage.postValue(e.message)
                _showLoading.postValue(false)
                onError(e)
            }
        }
    }

}