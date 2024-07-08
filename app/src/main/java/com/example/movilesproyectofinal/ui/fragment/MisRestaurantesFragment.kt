package com.example.movilesproyectofinal.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movilesproyectofinal.R
import com.example.movilesproyectofinal.databinding.FragmentMisRestaurantesBinding
import com.example.movilesproyectofinal.models.Restaurante
import com.example.movilesproyectofinal.repositories.PreferencesRepository
import com.example.movilesproyectofinal.ui.activities.MainActivity
import com.example.movilesproyectofinal.ui.adapters.RestaurantesAdapter
import com.example.movilesproyectofinal.ui.adapters.misRestaurantesAdapter
import com.example.movilesproyectofinal.ui.viewmodel.MisRestaurantesViewModel

class MisRestaurantesFragment : Fragment(), misRestaurantesAdapter.OnMisRestauranteClickListener {

    private lateinit var binding: FragmentMisRestaurantesBinding
    private val model: MisRestaurantesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMisRestaurantesBinding.inflate(inflater, container, false)

        Glide.with(this)
            .load(R.drawable.loading)
            .into(binding.imgLoading)

        setUpRecyclerView()
        setupViewModelObservers()

        model.fetchListaRestaurantes(PreferencesRepository.getToken(context)!!)


        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).binding.appBarRestaurantes.toolbar.visibility = View.VISIBLE
        (activity as MainActivity).binding.appBarRestaurantes.fab.hide()
    }



    fun setupViewModelObservers() {
        model.errorMessage.observe(viewLifecycleOwner) {
            if (it != "") {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }

        model.showLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.imgLoading.visibility = View.VISIBLE
            } else {
                binding.imgLoading.visibility = View.GONE
            }
        }

        model.restaurantes.observe(viewLifecycleOwner) {
            val lstRestaurantes = binding.lstMisRestaurantes
            lstRestaurantes.adapter = misRestaurantesAdapter(it, this)
        }

        model.deleteSuccess.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Restaurante eliminado", Toast.LENGTH_LONG).show()
                model.fetchListaRestaurantes(PreferencesRepository.getToken(context)!!)
            }
        }

    }

    fun setUpRecyclerView() {
        binding.lstMisRestaurantes.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = misRestaurantesAdapter(arrayListOf(), this@MisRestaurantesFragment)
        }
    }

    override fun onRestauranteClick(restaurante: Restaurante) {
        val bundle = Bundle()
        bundle.putLong("restauranteId", restaurante.id)
        bundle.putBoolean("isOwner", true)

        findNavController().navigate(R.id.nav_RestauranteDescripcion, bundle)
    }

    override fun editarRestaurante(restaurante: Restaurante) {
        val bundle = Bundle()
        bundle.putLong("id", restaurante.id)
        bundle.putString("nombre", restaurante.name)
        bundle.putString("direccion", restaurante.address)
        bundle.putString("descripcion", restaurante.description)
        bundle.putString("city", restaurante.city)

        findNavController().navigate(R.id.nav_CrearRestaurante, bundle)
    }

    override fun eliminarRestaurante(restaurante: Restaurante) {
        model.deleteRestaurante(PreferencesRepository.getToken(context)!!, restaurante.id.toInt())
    }

}