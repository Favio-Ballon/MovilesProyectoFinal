package com.example.movilesproyectofinal.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movilesproyectofinal.R
import com.example.movilesproyectofinal.databinding.FragmentRestauranteReservacionBinding
import com.example.movilesproyectofinal.models.Food
import com.example.movilesproyectofinal.repositories.PreferencesRepository
import com.example.movilesproyectofinal.ui.viewmodel.RestauranteReservacionViewModel
import com.example.movilesproyectofinal.ui.viewmodel.SharedViewModel

class RestauranteReservacionFragment : Fragment() {

    private val model : RestauranteReservacionViewModel by viewModels()
    private lateinit var binding: FragmentRestauranteReservacionBinding
    private var idRestaurante: Long? = null
    val sharedViewModel: SharedViewModel by activityViewModels()
    private var food : ArrayList<Food>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestauranteReservacionBinding.inflate(inflater, container, false)


        idRestaurante = arguments?.getLong("restauranteId")
        setUpViewModelObservers()
        setUpButtonListener()



        return binding.root
    }

    fun setUpViewModelObservers(){
        model.showLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.imgLoading.visibility = View.VISIBLE
            } else {
                binding.imgLoading.visibility = View.GONE
            }
        }

        model.errorMessage.observe(viewLifecycleOwner) {
            if (it != "") {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }

        model.reservacion.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Restaurante Reservado", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }

        sharedViewModel.selectedData.observe(viewLifecycleOwner) {
            food = it
        }

    }

    fun setUpButtonListener(){
        binding.btnAgregarReserva.setOnClickListener {

            val fecha = binding.etFecha.text.toString()
            val hora = binding.etHora.text.toString()
            val personasString = binding.etCantidadPersonas.text.toString()
            val personas = personasString.toLongOrNull()


            if (idRestaurante != null) {
                model.reservarRestaurante(idRestaurante, fecha, hora, personas,food ,PreferencesRepository.getToken(requireContext())!!)
            } else {
                Toast.makeText(context, "No se pudo obtener el id del restaurante", Toast.LENGTH_SHORT).show()
            }
        }
        binding.btnAgregarMenu.setOnClickListener {
            val bundle = Bundle()
            bundle.putLong("restauranteId", idRestaurante!!)
            bundle.putBoolean("isReservacion", true)
            findNavController().navigate(R.id.nav_Menu, bundle)
        }
    }


}