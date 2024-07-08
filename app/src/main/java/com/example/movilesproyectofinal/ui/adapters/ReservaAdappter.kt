package com.example.movilesproyectofinal.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movilesproyectofinal.databinding.FragmentRestaurantesBinding
import com.example.movilesproyectofinal.models.ReservaList
import com.example.movilesproyectofinal.databinding.ReservaItemLayoutBinding
import com.example.movilesproyectofinal.models.Reserva

class ReservaAdappter(
    val listaReservas: ReservaList,
    val listener: OnReservaClickListener
) : RecyclerView.Adapter<ReservaAdappter.ReservaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservaViewHolder {
        val binding = ReservaItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ReservaViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ReservaViewHolder, position: Int) {
        val reserva = listaReservas[position]
        holder.bind(reserva, listener)
    }

    override fun getItemCount(): Int {
        return listaReservas.size
    }

    class ReservaViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView)  {
        fun bind(reserva: Reserva, listener: OnReservaClickListener) {
            val binding = ReservaItemLayoutBinding.bind(itemView)
            binding.apply {
                textViewTitle.text = reserva.restaurant.name
                textViewDateTime.text = "Reservado para el ${reserva.date} a las ${reserva.time}"
                Glide.with(itemView).load(reserva.restaurant.logo).into(imageViewRestaurant)
                root.setOnClickListener {
                    listener.onReservaClick(reserva)
                }
            }

        }
    }
    interface OnReservaClickListener {
        abstract fun onReservaClick(reserva: Reserva)

    }

}

