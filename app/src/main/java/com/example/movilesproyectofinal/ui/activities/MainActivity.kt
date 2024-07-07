package com.example.movilesproyectofinal.ui.activities

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController


import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movilesproyectofinal.R
import com.example.movilesproyectofinal.databinding.ActivityRestaurantesBinding
import com.example.movilesproyectofinal.repositories.PreferencesRepository
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    lateinit var binding: ActivityRestaurantesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRestaurantesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkToken()


        setSupportActionBar(binding.appBarRestaurantes.toolbar)
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_restaurantes)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_Restaurantes, R.id.nav_Login, R.id.nav_Registrarse, R.id.logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if(PreferencesRepository.getIsLogged(this)){
            navView.menu.findItem(R.id.nav_Login).isVisible = false
            navView.menu.findItem(R.id.nav_Registrarse).isVisible = false
            navController.navigate(R.id.nav_Restaurantes)

        }else{
            navView.menu.findItem(R.id.logout).isVisible = false
            navView.menu.findItem(R.id.nav_Restaurantes).isVisible = true
            navView.menu.findItem(R.id.nav_Login).isVisible = true
            navView.menu.findItem(R.id.nav_Registrarse).isVisible = true
        }

        binding.appBarRestaurantes.fab.hide()
        setButtonListener()

        setLogout()
    }

    private fun checkToken(): Boolean {
        val token = PreferencesRepository.getToken(this)
        if (token != null && token != "") {
            return true
        }
        return false
    }

    fun isLogged() {
        val token = PreferencesRepository.getIsLogged(this)
        if (token) {
            binding.navView.menu.findItem(R.id.nav_Login).isVisible = false
            binding.navView.menu.findItem(R.id.nav_Registrarse).isVisible = false
            binding.navView.menu.findItem(R.id.logout).isVisible = true
        }else{
            binding.navView.menu.findItem(R.id.nav_Login).isVisible = true
            binding.navView.menu.findItem(R.id.nav_Registrarse).isVisible = true
            binding.navView.menu.findItem(R.id.logout).isVisible = false
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_restaurantes)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    fun setLogout() {
        binding.navView.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            cerrarSesion()
            true
        }
    }

    fun cerrarSesion() {
        UserRepository.doLogout(this)
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
        findNavController(R.id.nav_host_fragment_content_restaurantes).navigate(R.id.nav_main)
    }

    fun setButtonListener() {
        binding.appBarRestaurantes.fab.setOnClickListener {
            findNavController(R.id.nav_host_fragment_content_restaurantes).navigate(R.id.nav_RestaurantesFiltro)
        }
    }

}