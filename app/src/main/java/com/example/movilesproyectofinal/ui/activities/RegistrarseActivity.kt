package com.example.movilesproyectofinal.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels

import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.movilesproyectofinal.R
import com.example.movilesproyectofinal.databinding.ActivityRegistrarseBinding
import com.example.movilesproyectofinal.repositories.PreferencesRepository
import com.example.movilesproyectofinal.ui.viewmodel.RegistrarseViewModel

class RegistrarseActivity : AppCompatActivity() {

    val model: RegistrarseViewModel by viewModels()
    lateinit var binding: ActivityRegistrarseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegistrarseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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
        binding.btnRegistrarse.setOnClickListener {
            val username = binding.txtName.text.toString()
            val email = binding.txtEmail.text.toString()
            val password = binding.txtPassword.text.toString()
            val telefono = binding.txtTelefono.text.toString()
            model.registrarse(username, email, password, telefono, this)
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
                binding.imgLoading.visibility = android.view.View.VISIBLE
            } else {
                binding.imgLoading.visibility = android.view.View.GONE
            }
        }

        model.goToLogin.observe(this){
            if (it){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


}