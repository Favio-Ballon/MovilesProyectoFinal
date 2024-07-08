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
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.movilesproyectofinal.databinding.FragmentMenuBinding
import com.example.movilesproyectofinal.models.Food
import com.example.movilesproyectofinal.models.Menu
import com.example.movilesproyectofinal.models.Plate
import com.example.movilesproyectofinal.ui.adapters.MenuAdapter
import com.example.movilesproyectofinal.ui.adapters.PlatoAdapter
import com.example.movilesproyectofinal.ui.viewmodel.MenuViewModel
import com.example.movilesproyectofinal.ui.viewmodel.SharedViewModel

class MenuFragment : Fragment(), MenuAdapter.OnMenuClickListener,
    PlatoAdapter.OnPlatoClickListener {
    lateinit var binding: FragmentMenuBinding
    private val model: MenuViewModel by viewModels()
    private var isReservacion : Boolean = false
    val sharedViewModel: SharedViewModel by activityViewModels()

    private var food = ArrayList<Food>()

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
        isReservacion = arguments?.getBoolean("isReservacion") ?: false

        if (restaurante != null) {
            model.fetchListaMenus(restaurante.toInt())
        } else {
            Toast.makeText(context, "No se pudo obtener el id del restaurante", Toast.LENGTH_SHORT)
                .show()
        }

        if(isReservacion){
            binding.btnGuardarComida.visibility = View.VISIBLE
            setButtonListener()
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


        if (isReservacion) {
            binding.rvMenuPlato.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = PlatoAdapter(arrayListOf(),true ,food,this@MenuFragment)
            }
        } else {
            binding.rvMenuPlato.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = PlatoAdapter(arrayListOf(),false,food, this@MenuFragment)
            }
        }
    }

    override fun onMenuClick(menu: Menu) {
        val lstPlato = binding.rvMenuPlato
        if(isReservacion)
            lstPlato.adapter = PlatoAdapter(menu.plates, true, food,this)
        else{
            lstPlato.adapter = PlatoAdapter(menu.plates, false, food,this)
        }
    }

    override fun botonAumentar(plato: Plate) {
        val existingFood = food.find { it.plate_id == plato.id }
        if (existingFood != null) {
            food[food.indexOf(existingFood)].qty = (existingFood.qty.toInt() + 1).toString()
        } else {
            food.add(Food(plate_id = plato.id, qty = "1"))
        }
    }

    override fun botonDisminuir(plato: Plate) {
        val existingFood = food.find { it.plate_id == plato.id }
        if (existingFood != null) {
            if (existingFood.qty.toInt() > 1) {
                food[food.indexOf(existingFood)].qty = (existingFood.qty.toInt() - 1).toString()
            } else {
                food.remove(existingFood)
            }
        }
    }

    fun setButtonListener(){
        binding.btnGuardarComida.setOnClickListener {
            sharedViewModel.selectedData.value = food
            findNavController().popBackStack()
        }
    }


}