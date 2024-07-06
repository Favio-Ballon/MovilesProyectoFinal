package com.example.movilesproyectofinal.ui.activities

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.movilesproyectofinal.R
import com.example.movilesproyectofinal.databinding.ActivityLoginBinding
import com.example.movilesproyectofinal.repositories.PreferencesRepository

import com.example.movilesproyectofinal.ui.viewmodel.LoginViewModel

class LoginActivity : AppCompatActivity() {

    val model: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        checkToken()
        setupEventListeners()
        setupViewModelObservers()
        Glide.with(this)
            .load(R.drawable.loading)
            .into(binding.imgLoading)
    }

    private fun checkToken() {
        val token = PreferencesRepository.getToken(this)
        if (token != null) {
            Toast.makeText(this, "El token es: $token", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupEventListeners() {
        binding.btnLogin.setOnClickListener {
            val user = binding.username.text.toString()
            val password = binding.password.text.toString()
            model.login(user, password, this)
        }
    }

    private fun setupViewModelObservers() {
        model.errorMessage.observe(this) {
            if (it.isNotEmpty()) {
                Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
            }
        }
        model.showLoading.observe(this) {
            if (it) {
                binding.imgLoading.visibility = View.VISIBLE
            } else {
                binding.imgLoading.visibility = View.GONE
            }
        }
        model.goToRestaurantes.observe(this) {
            if (it) {
                val intent = Intent(this, RestaurantesActivity::class.java)
                startActivity(intent)
            }
        }
    }


}

