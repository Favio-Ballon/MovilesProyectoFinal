package com.example.movilesproyectofinal.ui.activities

import UserRepository
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.movilesproyectofinal.R
import com.example.movilesproyectofinal.databinding.ActivityRestaurantesBinding

class RestaurantesActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityRestaurantesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRestaurantesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarRestaurantes.toolbar)

        binding.appBarRestaurantes.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_restaurantes)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow, R.id.nav_Restaurantes, R.id.logout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        setLogout()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.restaurantes, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_restaurantes)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    fun setLogout(){
        binding.navView.menu.findItem(R.id.logout).setOnMenuItemClickListener {
            cerrarSesion()
            true
        }
    }

    fun cerrarSesion(){
        UserRepository.doLogout(this)
        intent = Intent(this, MainActivity::class.java)
        finish()
    }

}