package com.example.movilesproyectofinal.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide

import com.example.movilesproyectofinal.databinding.FragmentRestaurantesBinding
import com.example.movilesproyectofinal.models.Restaurante
import com.example.movilesproyectofinal.models.Restaurantes

class RestaurantesAdapter(
    val RestauranteList: Restaurantes, val listener: OnRestauranteClickListener
) : RecyclerView.Adapter<RestaurantesAdapter.RestauranteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestauranteViewHolder {

        val binding = FragmentRestaurantesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RestauranteViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RestauranteViewHolder, position: Int) {
        val restaurante = RestauranteList[position]
        holder.bind(restaurante, listener)
    }

    override fun getItemCount(): Int = RestauranteList.size

    class RestauranteViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
fun bind(restaurante: Restaurante, listener: OnRestauranteClickListener) {
            val binding = FragmentRestaurantesBinding.bind(itemView)
            binding.apply {
                lblNombreRestaurante.text = restaurante.name
                Glide.with(itemView).load(restaurante.logo).into(imgRestaurante)
                root.setOnClickListener {
                    listener.onRestauranteClick(restaurante)
                }
            }
        }
    }

    interface OnRestauranteClickListener {
        fun onRestauranteClick(restaurante: Restaurante)

    }

}