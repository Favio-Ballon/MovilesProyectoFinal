package com.example.movilesproyectofinal.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.movilesproyectofinal.R
import com.example.movilesproyectofinal.databinding.FragmentRestauranteDescripcionBinding
import com.example.movilesproyectofinal.ui.adapters.ImageAdapter
import com.example.movilesproyectofinal.ui.viewmodel.RestauranteDescripcionViewModel


class RestauranteDescripcionFragment : Fragment(), ImageAdapter.OnGaleriaClickListener{

    private val model : RestauranteDescripcionViewModel by viewModels()

    private lateinit var binding : FragmentRestauranteDescripcionBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRestauranteDescripcionBinding.inflate(inflater, container, false)


        setupViewModelObservers()
        setImageLoading()
        setUpRecyclerView()

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
        model.errorMessage.observe(this) {
            if (it != null) {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
        }
        model.showLoading.observe(this) {
            if (it) {
                binding.imgLoading.visibility = View.VISIBLE
            } else {
                binding.imgLoading.visibility = View.GONE
            }
        }
        model.restaurante.observe(this) {
            if (it == null) {
                return@observe
            }
            binding.nameTextView.text = it.name
            binding.descriptionTextView.text = it.description
            binding.addressTextView.text = it.address
            binding.cityTextView.text = it.city
            Glide.with(this)
                .load(it.logo)
                .into(binding.logoImageView)
            binding.photosRecyclerView.adapter = ImageAdapter(it.photos, this)
            Log.d("RestauranteDescripcionFragment", "Restaurante: $it")
        }
    }

    fun setUpRecyclerView(){
        binding.photosRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = ImageAdapter(arrayListOf(), this@RestauranteDescripcionFragment)
        }
    }

    override fun onGaleriaClick(galeria: String) {
        // make image full screen



    }


}