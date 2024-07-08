package com.example.movilesproyectofinal.ui.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.movilesproyectofinal.models.Plate
import com.example.movilesproyectofinal.databinding.PlatoItemLayoutBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.movilesproyectofinal.models.Food

class PlatoAdapter(
    private val platos: List<Plate>,
    private val isReservacion: Boolean,
    private var food : ArrayList<Food>,
    private val listener: OnPlatoClickListener
) : RecyclerView.Adapter<PlatoAdapter.PlatoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatoViewHolder {
        val binding = PlatoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlatoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlatoViewHolder, position: Int) {
        val plato = platos[position]
        holder.bind(plato, listener)
    }

    override fun getItemCount(): Int = platos.size

    inner class PlatoViewHolder(private val binding: PlatoItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(plato: Plate, listener: OnPlatoClickListener) {
            binding.txtPlatoNombre.text = plato.name
            binding.txtPrecio.text = plato.price
            binding.txtPlatoDescripcion.text = plato.description

            // check if id is in food
            if (food.find { it.plate_id == plato.id } != null) {
                binding.txttNumberQuantity.text = food.find { it.plate_id == plato.id }!!.qty
            } else {
                binding.txttNumberQuantity.text = "0"
            }

            Log.d("PlatoAdapter", "Entrando en el bind ${isReservacion}")
            if (isReservacion) {
                Log.d("PlatoAdapter", "Entrando en el if")
                binding.numberButtons.visibility = View.VISIBLE

                binding.btnDecrease.setOnClickListener {
                    val cantidad = binding.txttNumberQuantity.text.toString().toInt()
                    if (cantidad > 0) {
                        binding.txttNumberQuantity.text = (cantidad - 1).toString()
                        listener.botonDisminuir(plato)
                    }
                }
                binding.btnIncrease.setOnClickListener {
                    val cantidad = binding.txttNumberQuantity.text.toString().toInt()
                    binding.txttNumberQuantity.text = (cantidad + 1).toString()
                    listener.botonAumentar(plato)
                }
            }


        }
    }

    interface OnPlatoClickListener {
        fun botonAumentar(plato: Plate)
        fun botonDisminuir(plato: Plate)
    }
}