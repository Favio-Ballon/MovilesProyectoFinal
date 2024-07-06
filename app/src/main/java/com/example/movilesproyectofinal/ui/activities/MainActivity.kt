package com.example.movilesproyectofinal.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.movilesproyectofinal.R
import com.example.movilesproyectofinal.databinding.ActivityMainBinding
import com.example.movilesproyectofinal.repositories.PreferencesRepository
import com.example.movilesproyectofinal.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    val model: MainViewModel by viewModels();
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupViewModelObservers()
        checkToken()
        setButton()
    }

    private fun checkToken() {
        val token = PreferencesRepository.getToken(this)
        if (token != null && token != "") {
            model.goToVisitante()
        }
    }

    private fun setButton() {
        binding.btnLogin.setOnClickListener {
            model.goToLogin()
        }

        binding.btnRegistrarse.setOnClickListener{
            model.goToRegistrarse()
        }

        binding.btnVisitante.setOnClickListener {
            model.goToVisitante()
        }
    }

    private fun setupViewModelObservers() {
        model.login.observe(this) {
            if (it) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        model.register.observe(this) {
            if (it) {
                val intent = Intent(this, RegistrarseActivity::class.java)
                startActivity(intent)
            }
        }

        model.visitante.observe(this) {
            if (it) {
                val intent = Intent(this, RestaurantesActivity::class.java)
                startActivity(intent)
            }
        }
    }

}