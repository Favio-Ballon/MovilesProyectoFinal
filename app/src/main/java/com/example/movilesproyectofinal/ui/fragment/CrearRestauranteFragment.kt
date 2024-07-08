package com.example.movilesproyectofinal.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.movilesproyectofinal.R
import com.example.movilesproyectofinal.databinding.FragmentCrearRestauranteBinding
import com.example.movilesproyectofinal.repositories.PreferencesRepository
import com.example.movilesproyectofinal.ui.activities.MainActivity
import com.example.movilesproyectofinal.ui.viewmodel.CrearRestauranteViewModel

class CrearRestauranteFragment : Fragment() {

    private val model: CrearRestauranteViewModel by viewModels()

    lateinit var binding: FragmentCrearRestauranteBinding

    private var id: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCrearRestauranteBinding.inflate(inflater, container, false)

        setUpViewModelObservers()
        setUpListeners()


        /*
        bundle.putLong("id", restaurante.id)
        bundle.putString("nombre", restaurante.name)
        bundle.putString("direccion", restaurante.address)
        bundle.putString("descripcion", restaurante.description)
        bundle.putString("city", restaurante.city)
         */

        id = arguments?.getLong("id")
        val nombre = arguments?.getString("nombre")
        val direccion = arguments?.getString("direccion")
        val descripcion = arguments?.getString("descripcion")
        val city = arguments?.getString("city")
        if (id != null) {
            binding.name.setText(nombre)
            binding.address.setText(direccion)
            binding.description.setText(descripcion)
            binding.city.setText(city)

            setButtonEditar()
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

    fun setUpViewModelObservers() {
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

        model.crearRestaurante.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Restaurante creado", Toast.LENGTH_LONG).show()
                findNavController().popBackStack()
            }
        }
    }

    fun setUpListeners() {
        binding.btnCreate.setOnClickListener {
            val nombre = binding.name.text.toString()
            val descripcion = binding.description.text.toString()
            val direccion = binding.address.text.toString()
            val city = binding.city.text.toString()

            if (id == null) {
                model.crearRestaurante(
                    nombre,
                    descripcion,
                    direccion,
                    city,
                    PreferencesRepository.getToken(requireContext())!!
                )
            } else {
                model.editarRestaurante(
                    id!!.toInt(),
                    nombre,
                    descripcion,
                    direccion,
                    city,
                    PreferencesRepository.getToken(requireContext())!!
                )
            }
        }
    }

    fun setButtonEditar(){
        binding.btnCreate.text = "Editar"
        binding.btnChangeLogo.visibility = View.VISIBLE
        binding.btnAddPhotoGallery.visibility = View.VISIBLE

        binding.btnChangeLogo.setOnClickListener {
            val bundle = Bundle()
            bundle.putLong("id", id!!)
            bundle.putBoolean("isLogo", true)
            findNavController().navigate(R.id.nav_subirImagen, bundle)
        }

        binding.btnAddPhotoGallery.setOnClickListener {
            val bundle = Bundle()
            bundle.putLong("id", id!!)
            bundle.putBoolean("isLogo", false)
            findNavController().navigate(R.id.nav_subirImagen, bundle)
        }
    }
}