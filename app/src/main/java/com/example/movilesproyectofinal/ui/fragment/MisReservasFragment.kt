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
import com.example.movilesproyectofinal.databinding.FragmentMisReservasBinding
import com.example.movilesproyectofinal.models.Reserva
import com.example.movilesproyectofinal.repositories.PreferencesRepository
import com.example.movilesproyectofinal.ui.activities.MainActivity
import com.example.movilesproyectofinal.ui.adapters.ReservaAdappter
import com.example.movilesproyectofinal.ui.adapters.RestaurantesAdapter
import com.example.movilesproyectofinal.ui.viewmodel.MisReservasViewModel


class MisReservasFragment : Fragment(), ReservaAdappter.OnReservaClickListener {


    private val model : MisReservasViewModel by viewModels()
    lateinit var binding : FragmentMisReservasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMisReservasBinding.inflate(inflater, container, false)

        setUpRecyclerView()
        setupViewModelObservers()
        model.fetchReservas(PreferencesRepository.getToken(context))

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).binding.appBarRestaurantes.toolbar.visibility = View.VISIBLE
        (activity as MainActivity).binding.appBarRestaurantes.fab.hide()
    }

    fun setupViewModelObservers() {
        Glide.with(this)
            .load(R.drawable.loading)
            .into(binding.imgLoading)
        model.reserva.observe(viewLifecycleOwner) {
            if (it != null) {
                val lstRestaurantes = binding.rvReservas
                lstRestaurantes.adapter = ReservaAdappter(it, this)
            }
        }
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
    }

    fun setUpRecyclerView() {
        binding.rvReservas.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = ReservaAdappter(arrayListOf(), this@MisReservasFragment)
        }
    }

    override fun onReservaClick(reserva: Reserva) {
        val bundle = Bundle()
        bundle.putLong("id", reserva.id)
        bundle.putString("fecha", reserva.date)
        bundle.putString("hora", reserva.time)
        bundle.putString("restauranteName", reserva.restaurant.name)
        bundle.putString("descripcion", reserva.restaurant.description)
        bundle.putString("logo", reserva.restaurant.logo)
        bundle.putString("status", reserva.status)

        findNavController().navigate(R.id.nav_reservaDescripcion, bundle)
    }


}