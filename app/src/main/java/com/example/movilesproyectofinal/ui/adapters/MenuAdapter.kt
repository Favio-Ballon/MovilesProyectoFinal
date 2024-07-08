package com.example.movilesproyectofinal.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movilesproyectofinal.models.Menus
import com.example.movilesproyectofinal.databinding.MenuItemLayoutBinding
import com.example.movilesproyectofinal.models.Menu

class MenuAdapter(
    private val menuList: Menus,
    private val isOwner: Boolean,
    private val listener: OnMenuClickListener
) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding =
            MenuItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menu = menuList[position]
        holder.bind(menu)
    }

    override fun getItemCount(): Int = menuList.size

    inner class MenuViewHolder(private val binding: MenuItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(menu: Menu) {

            binding.menuItemButton.text = menu.name
            if (isOwner) {

                binding.editButton.visibility = View.VISIBLE
                binding.deleteButton.visibility = View.VISIBLE
                binding.editButton.setOnClickListener {
                    listener.onMenuEditarClick(menu)
                }
                binding.deleteButton.setOnClickListener {
                    listener.onMenuBorrarClick(menu)
                }

            }
                binding.menuItemButton.setOnClickListener {
                    listener.onMenuClick(menu)
            }
        }
    }

    interface OnMenuClickListener {
        fun onMenuClick(menu: Menu)
        fun onMenuEditarClick(menu: Menu)
        fun onMenuBorrarClick(menu: Menu)

    }
}

