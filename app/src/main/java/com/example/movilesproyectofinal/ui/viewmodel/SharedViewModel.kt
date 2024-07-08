package com.example.movilesproyectofinal.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movilesproyectofinal.models.Food

class SharedViewModel : ViewModel() {
    val selectedData = MutableLiveData<ArrayList<Food>>()
}