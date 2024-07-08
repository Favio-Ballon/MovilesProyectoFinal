package com.example.movilesproyectofinal.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.movilesproyectofinal.R
import com.example.movilesproyectofinal.databinding.FragmentReservaDescripcionBinding
import com.example.movilesproyectofinal.repositories.PreferencesRepository
import com.example.movilesproyectofinal.ui.activities.MainActivity
import com.example.movilesproyectofinal.ui.viewmodel.ReservaDescripcionViewModel

class ReservaDescripcionFragment : Fragment() {

    lateinit var binding : FragmentReservaDescripcionBinding
    var id : Long? = null //restaurant id
    val model : ReservaDescripcionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentReservaDescripcionBinding.inflate(layoutInflater, container, false)


        val bundle = arguments
        val nombre = bundle?.getString("restauranteName")
        val fecha = bundle?.getString("fecha")
        val hora = bundle?.getString("hora")
        val descripcion = bundle?.getString("descripcion")
        val link = bundle?.getString("logo")
        val estado = bundle?.getString("status")
        id = bundle?.getLong("id")

    if (bundle != null) {

        binding.tituloRestaurante.text = nombre
        binding.fechaReserva.text = fecha
        binding.horaReserva.text = hora
        binding.descripcionRestaurante.text = descripcion
        binding.tvEstado.text = "Estado: $estado"
        Glide.with(this)
            .load(link)
            .into(binding.logoRestaurante)
    }
        setUpViewModelListener()
        setButtonListener()
        if(estado == "canceled"){
            binding.btnCancelarReserva.isVisible = false
        }

        Glide.with(this)
            .load(R.drawable.loading)
            .into(binding.imgLoading)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).binding.appBarRestaurantes.toolbar.visibility = View.VISIBLE
        (activity as MainActivity).binding.appBarRestaurantes.fab.hide()
    }

    fun setButtonListener(){
        binding.btnCancelarReserva.setOnClickListener {
            model.cancelarReserva(id!!, PreferencesRepository.getToken(context)!!)
        }
    }

    fun setUpViewModelListener(){
        model.errorMessage.observe(viewLifecycleOwner){
            if(it != ""){
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
        model.showLoading.observe(viewLifecycleOwner){
            if(it){
                binding.imgLoading.visibility = View.VISIBLE
            }else{
                binding.imgLoading.visibility = View.GONE
            }
        }
        model.cancelado.observe(viewLifecycleOwner){
            if(it){
                binding.tvEstado.text = "Estado: canceled"
                binding.btnCancelarReserva.isVisible = false
            }
        }
    }




}