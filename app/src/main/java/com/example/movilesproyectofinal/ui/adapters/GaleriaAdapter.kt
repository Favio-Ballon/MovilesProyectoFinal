package com.example.movilesproyectofinal.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movilesproyectofinal.R
import com.example.movilesproyectofinal.databinding.GaleriaItemBinding
import com.example.movilesproyectofinal.models.Photo

class GaleriaAdapter(
    private val fotos: List<Photo>,
    val listener: OnGaleriaClickListener
) : RecyclerView.Adapter<GaleriaAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = GaleriaItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ImageViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUrl = fotos[position].url
        holder.bind(imageUrl, listener)
    }

    override fun getItemCount(): Int {
        return fotos.size
    }

    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(imageUrl: String, listener: OnGaleriaClickListener) {
            val binding = GaleriaItemBinding.bind(itemView)


            // Check if the image URL ends with ".avif"
            if (imageUrl.endsWith(".avif")) {
                // Handle AVIF image loading separately
                // For example, show a placeholder or an error image
                binding.imageView.setImageResource(R.drawable.loading)
            } else {
                // For non-AVIF images, continue using Glide as usual
                Glide.with(itemView.context)
                    .load(imageUrl)
                    .into(binding.imageView)
            }


            binding.root.setOnClickListener {
                listener.onGaleriaClick(imageUrl)
            }

        }
    }

    interface OnGaleriaClickListener {
        fun onGaleriaClick(galeria: String)
    }
}
