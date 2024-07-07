package com.example.movilesproyectofinal.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.movilesproyectofinal.models.Plate
import com.example.movilesproyectofinal.databinding.PlatoItemLayoutBinding
import androidx.recyclerview.widget.RecyclerView

class PlatoAdapter(
    private val platos: List<Plate>,
    private val onPlatoClickListener: OnPlatoClickListener
) : RecyclerView.Adapter<PlatoAdapter.PlatoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatoViewHolder {
        val binding = PlatoItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlatoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlatoViewHolder, position: Int) {
        val plato = platos[position]
        holder.bind(plato)
    }

    override fun getItemCount(): Int = platos.size

    inner class PlatoViewHolder(private val binding: PlatoItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(plato: Plate) {
            binding.txtPlatoNombre.text = plato.name
            binding.txtPrecio.text = plato.price
            binding.txtPlatoDescripcion.text = plato.description

            itemView.setOnClickListener {
                onPlatoClickListener.onPlatoClick(plato)
            }
        }
    }

    interface OnPlatoClickListener {
        fun onPlatoClick(plato: Plate)
    }
}