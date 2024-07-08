package com.example.movilesproyectofinal.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.movilesproyectofinal.R
import com.example.movilesproyectofinal.databinding.FragmentReservaDescripcionBinding
import com.example.movilesproyectofinal.ui.activities.MainActivity

class ReservaDescripcionFragment : Fragment() {

    lateinit var binding : FragmentReservaDescripcionBinding
    var id : Long? = null //restaurant id

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReservaDescripcionBinding.inflate(layoutInflater, container, false)

       /*
       bundle.putLong("restaurant_id", reserva.restaurant_id)
        bundle.putString("fecha", reserva.date)
        bundle.putString("hora", reserva.time)
        bundle.putString("restauranteName", reserva.restaurant.name)
        bundle.putString("descripcion", reserva.restaurant.description)
        bundle.putString("logo", reserva.restaurant.logo)

        */
        val bundle = arguments
        val nombre = bundle?.getString("restauranteName")
        val fecha = bundle?.getString("fecha")
        val hora = bundle?.getString("hora")
        val descripcion = bundle?.getString("descripcion")
        val link = bundle?.getString("logo")
        id = bundle?.getLong("restaurant_id")

    if (bundle != null) {

        binding.tituloRestaurante.text = nombre
        binding.fechaReserva.text = fecha
        binding.horaReserva.text = hora
        binding.descripcionRestaurante.text = descripcion
        Glide.with(this)
            .load(link)
            .into(binding.logoRestaurante)
    }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).binding.appBarRestaurantes.toolbar.visibility = View.VISIBLE
        (activity as MainActivity).binding.appBarRestaurantes.fab.hide()
    }



}