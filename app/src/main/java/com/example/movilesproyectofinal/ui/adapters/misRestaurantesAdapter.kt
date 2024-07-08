package com.example.movilesproyectofinal.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movilesproyectofinal.databinding.MisRestaurantesItemLayoutBinding
import com.example.movilesproyectofinal.models.Restaurante
import com.example.movilesproyectofinal.models.Restaurantes

class misRestaurantesAdapter(
    val restaurantes: Restaurantes,
    val listener: OnMisRestauranteClickListener
) : RecyclerView.Adapter<misRestaurantesAdapter.RestauranteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestauranteViewHolder {

       val binding = MisRestaurantesItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RestauranteViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return restaurantes.size
    }

    override fun onBindViewHolder(holder: RestauranteViewHolder, position: Int) {
        val item = restaurantes[position]
        holder.bind(item, listener)
    }

    class RestauranteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(restaurante: Restaurante, listener: OnMisRestauranteClickListener) {
            val binding = MisRestaurantesItemLayoutBinding.bind(itemView)
            binding.btnEdit
            binding.apply {
                tituloRestaurante.text = restaurante.name
                Glide.with(itemView).load(restaurante.logo).into(logoRestaurante)
                root.setOnClickListener {
                    listener.onRestauranteClick(restaurante)
                }
                btnEdit.setOnClickListener {
                    listener.editarRestaurante(restaurante)
                }
                btnDelete.setOnClickListener {
                    listener.eliminarRestaurante(restaurante)
                }
            }
        }
    }

    interface OnMisRestauranteClickListener {
        fun onRestauranteClick(restaurante: Restaurante)
        abstract fun editarRestaurante(restaurante: Restaurante)
        fun eliminarRestaurante(restaurante: Restaurante)

    }
}