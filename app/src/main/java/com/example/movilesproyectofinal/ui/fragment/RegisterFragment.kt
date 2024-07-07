package com.example.movilesproyectofinal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.movilesproyectofinal.R
import com.example.movilesproyectofinal.databinding.ActivityRegistrarseBinding
import com.example.movilesproyectofinal.repositories.PreferencesRepository
import com.example.movilesproyectofinal.ui.viewmodel.RegistrarseViewModel
import androidx.navigation.fragment.findNavController

class RegisterFragment : Fragment() {

    val model: RegistrarseViewModel by viewModels()
    lateinit var binding: ActivityRegistrarseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityRegistrarseBinding.inflate(inflater, container, false)

        setupEventListeners()
        setupViewModelObservers()

        Glide.with(this)
            .load(R.drawable.loading)
            .into(binding.imgLoading)

        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()

    }


    private fun setupEventListeners() {
        binding.btnRegistrarse.setOnClickListener {
            val username = binding.txtName.text.toString()
            val email = binding.txtEmail.text.toString()
            val password = binding.txtPassword.text.toString()
            val telefono = binding.txtTelefono.text.toString()
            model.registrarse(username, email, password, telefono)
        }
    }

    private fun setupViewModelObservers() {
        model.errorMessage.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }

        model.showLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.imgLoading.visibility = android.view.View.VISIBLE
            } else {
                binding.imgLoading.visibility = android.view.View.GONE
            }
        }

        model.goToLogin.observe(viewLifecycleOwner){
            if(it){
                findNavController().navigate(R.id.nav_main)

            }
        }
    }
}