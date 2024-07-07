package com.example.movilesproyectofinal.ui.fragment

import android.os.Bundle
import android.util.Log
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
import com.example.movilesproyectofinal.databinding.FragmentRestauranteDescripcionBinding
import com.example.movilesproyectofinal.ui.adapters.GaleriaAdapter
import com.example.movilesproyectofinal.ui.viewmodel.RestauranteDescripcionViewModel


class RestauranteDescripcionFragment : Fragment(), GaleriaAdapter.OnGaleriaClickListener{

    private val model : RestauranteDescripcionViewModel by viewModels()

    private lateinit var binding : FragmentRestauranteDescripcionBinding
    private var idRestaurante : Long = 0

    var containernombre: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRestauranteDescripcionBinding.inflate(inflater, container, false)

        containernombre = R.id.container

        setupViewModelObservers()
        setImageLoading()
        setUpRecyclerView()
        setButtonListener()

        //conseguimos el id del restaurante
        val restaurante = arguments?.getLong("restauranteId")
        if (restaurante != null) {
            model.fetchRestauranteDescripcion(restaurante.toInt())
        }else{
            Toast.makeText(context, "No se pudo obtener el id del restaurante", Toast.LENGTH_SHORT).show()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    fun setImageLoading(){
        Glide.with(this)
            .load(R.drawable.loading)
            .into(binding.imgLoading)
    }

    fun setupViewModelObservers() {
        model.errorMessage.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
        model.showLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.imgLoading.visibility = View.VISIBLE
            } else {
                binding.imgLoading.visibility = View.GONE
            }
        }
        model.restaurante.observe(viewLifecycleOwner) {
            if (it == null) {
                return@observe
            }
            idRestaurante = it.id
            binding.nameTextView.text = it.name
            binding.descriptionTextView.text = it.description
            var ubicacion = "Ubicacion: " + it.address
            var ciudad = "Ciudad: " + it.city
            binding.addressTextView.text = ubicacion
            binding.cityTextView.text = ciudad
            Glide.with(this)
                .load(it.logo)
                .into(binding.logoImageView)
            binding.photosRecyclerView.adapter = GaleriaAdapter(it.photos, this)
            Log.d("RestauranteDescripcionFragment", "Restaurante: $it")
        }
    }

    fun setUpRecyclerView(){
        binding.photosRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = GaleriaAdapter(arrayListOf(), this@RestauranteDescripcionFragment)
        }
    }

    override fun onGaleriaClick(galeria: String) {
        // make image full screen
        val fullScreenImageFragment = FullScreenImageFragment.newInstance(galeria)


        childFragmentManager.beginTransaction()
            .replace(binding.root.id, fullScreenImageFragment)
            .addToBackStack(null)
            .commit()

        //Toast.makeText(context, galeria, Toast.LENGTH_SHORT).show()


    }

    fun setButtonListener(){
        binding.btnMenu.setOnClickListener {
            Toast.makeText(context, "Se apreto menu", Toast.LENGTH_SHORT).show()
            val bundle = Bundle()
            bundle.putLong("restauranteId", idRestaurante)
            findNavController().navigate(R.id.nav_Menu, bundle)
        }

    }


}