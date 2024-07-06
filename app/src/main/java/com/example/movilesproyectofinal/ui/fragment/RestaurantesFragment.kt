package com.example.movilesproyectofinal.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.movilesproyectofinal.R
import com.example.movilesproyectofinal.databinding.FragmentRestaurantesBinding
import com.example.movilesproyectofinal.databinding.FragmentRestaurantesListBinding
import com.example.movilesproyectofinal.models.Restaurante
import com.example.movilesproyectofinal.models.Restaurantes
import com.example.movilesproyectofinal.repositories.PreferencesRepository
import com.example.movilesproyectofinal.ui.adapters.RestaurantesAdapter
import com.example.movilesproyectofinal.ui.viewmodel.RestaurantesViewModel

/**
 * A fragment representing a list of Items.
 */
class RestaurantesFragment : Fragment(), RestaurantesAdapter.OnRestauranteClickListener {

    private val model : RestaurantesViewModel by viewModels()
    private lateinit var binding: FragmentRestaurantesListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkToken()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantesListBinding.inflate(inflater, container, false)
        setUpRecyclerView()
        setupViewModelObservers()
        model.fetchListaRestaurantes()
        return binding.root
    }

    private fun checkToken() {
        val token = PreferencesRepository.getToken(context)
        if (token != null) {
            Toast.makeText(context, "El token es: $token", Toast.LENGTH_SHORT).show()
        }
    }

    fun setupViewModelObservers() {
        Glide.with(this)
            .load(R.drawable.loading)
            .into(binding.imgLoading)
        model.restaurantes.observe(this) {
            val lstRestaurantes = binding.list
            lstRestaurantes.adapter = RestaurantesAdapter(it, this)
        }
        model.errorMessage.observe(this) {
            if (it != "") {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }

        model.showLoading.observe(this) {
            if (it) {
                binding.imgLoading.visibility = View.VISIBLE
            } else {
                binding.imgLoading.visibility = View.GONE
            }
        }
    }

    fun setUpRecyclerView() {
        binding.list.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = RestaurantesAdapter(arrayListOf(), this@RestaurantesFragment)
        }
    }

    override fun onRestauranteClick(restaurante: Restaurante) {
        TODO("Not yet implemented")
    }
}