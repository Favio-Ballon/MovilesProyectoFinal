package com.example.movilesproyectofinal.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.movilesproyectofinal.R
import com.example.movilesproyectofinal.databinding.FragmentRestaurantesBinding
import com.example.movilesproyectofinal.models.Restaurante
import com.example.movilesproyectofinal.models.Restaurantes
import com.example.movilesproyectofinal.ui.adapters.RestaurantesAdapter
import com.example.movilesproyectofinal.ui.viewmodel.RestaurantesViewModel

/**
 * A fragment representing a list of Items.
 */
class RestaurantesFragment : Fragment(), RestaurantesAdapter.OnRestauranteClickListener {

    private val model : RestaurantesViewModel by viewModels()
    private lateinit var binding: FragmentRestaurantesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModelObservers()
        model.fetchListaRestaurantes()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRestaurantesBinding.inflate(inflater, container, false)
        val view = binding.root

        return view
    }

    fun setupViewModelObservers() {
        model.restaurantes.observe(this) {
            val lstRestaurantes = binding.root.findViewById<RecyclerView>(R.id.list)
            lstRestaurantes.adapter = RestaurantesAdapter(it, this)
        }
    }

    override fun onRestauranteClick(restaurante: Restaurante) {
        TODO("Not yet implemented")
    }
}