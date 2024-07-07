package com.example.movilesproyectofinal.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movilesproyectofinal.databinding.FragmentMenuBinding
import com.example.movilesproyectofinal.models.Menu
import com.example.movilesproyectofinal.models.Plate
import com.example.movilesproyectofinal.ui.adapters.MenuAdapter
import com.example.movilesproyectofinal.ui.adapters.PlatoAdapter
import com.example.movilesproyectofinal.ui.viewmodel.MenuViewModel

class MenuFragment : Fragment(), MenuAdapter.OnMenuClickListener, PlatoAdapter.OnPlatoClickListener{
    lateinit var binding: FragmentMenuBinding
    private val model : MenuViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMenuBinding.inflate(inflater, container, false)
        setupViewModelObservers()
        setUpRecyclerView()
        //model.fetchListaMenus()

        val restaurante = arguments?.getLong("restauranteId")
        if (restaurante != null) {
            model.fetchListaMenus(restaurante.toInt())
        }else{
            Toast.makeText(context, "No se pudo obtener el id del restaurante", Toast.LENGTH_SHORT).show()
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    fun setupViewModelObservers() {
        model.showLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.imgLoading.visibility = View.VISIBLE
            } else {
                binding.imgLoading.visibility = View.GONE
            }
        }
        model.menus.observe(viewLifecycleOwner) {
            val lstMenu = binding.rvMenuCategoria
            lstMenu.adapter = MenuAdapter(it, this)
        }
    }

    fun setUpRecyclerView() {
        binding.rvMenuCategoria.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = MenuAdapter(arrayListOf(), this@MenuFragment)
        }

        binding.rvMenuPlato.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = PlatoAdapter(arrayListOf(), this@MenuFragment)
        }
    }

    override fun onMenuClick(menu: Menu) {
        val lstPlato = binding.rvMenuPlato
        lstPlato.adapter = PlatoAdapter(menu.plates, this)
    }

    override fun onPlatoClick(plato: Plate) {
        TODO("Not yet implemented")
    }

}