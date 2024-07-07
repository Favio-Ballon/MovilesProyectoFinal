package com.example.movilesproyectofinal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.movilesproyectofinal.R
import com.example.movilesproyectofinal.databinding.ActivityLoginBinding
import com.example.movilesproyectofinal.repositories.PreferencesRepository
import com.example.movilesproyectofinal.ui.viewmodel.LoginViewModel

class LoginFragment : Fragment() {

    lateinit var binding : ActivityLoginBinding
    private val model: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityLoginBinding.inflate(inflater, container, false)

        checkToken()
        setupEventListeners()
        setupViewModelObservers()
        Glide.with(this)
            .load(R.drawable.loading)
            .into(binding.imgLoading)
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun checkToken() {
        val token = PreferencesRepository.getToken(requireContext())
        if (token != null) {
            Toast.makeText(requireContext(), "El token es: $token", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupEventListeners() {
        binding.btnLogin.setOnClickListener {
            val user = binding.username.text.toString()
            val password = binding.password.text.toString()
            model.login(user, password, requireContext())
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
                binding.imgLoading.visibility = View.VISIBLE
            } else {
                binding.imgLoading.visibility = View.GONE
            }
        }
        model.goToRestaurantes.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.nav_Restaurantes)
            }
        }
    }

}