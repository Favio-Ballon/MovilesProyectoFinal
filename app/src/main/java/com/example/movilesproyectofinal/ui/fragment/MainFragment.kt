package com.example.movilesproyectofinal.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.movilesproyectofinal.R
import com.example.movilesproyectofinal.databinding.ActivityMainBinding
import com.example.movilesproyectofinal.repositories.PreferencesRepository
import com.example.movilesproyectofinal.ui.activities.MainActivity
import com.example.movilesproyectofinal.ui.viewmodel.MainFragmentViewModel

class MainFragment : Fragment() {

    val model: MainFragmentViewModel by viewModels();
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityMainBinding.inflate(inflater, container, false)

        setupViewModelObservers()
        setButton()


        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        checkLogin()
        //(activity as MainActivity).binding.appBarRestaurantes.toolbar.visibility = View.GONE
        (activity as MainActivity).binding.appBarRestaurantes.fab.hide()
        //hide navigate up
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        //close menu if it is open
        (activity as MainActivity).binding.drawerLayout.close()
    }

    private fun checkLogin() {
        val token = PreferencesRepository.getIsLogged(context)
        if (token) {
            findNavController().navigate(R.id.nav_Restaurantes)
        }
    }


    private fun setButton() {
        binding.btnLogin.setOnClickListener {
            model.goToLogin()
        }

        binding.btnRegistrarse.setOnClickListener{
            model.goToRegistrarse()
        }

        binding.btnVisitante.setOnClickListener {
            model.goToVisitante()
        }
    }

    private fun setupViewModelObservers() {
        model.login.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.nav_Login)
                model.goToLoginComplete()
            }
        }

        model.register.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.nav_Registrarse)
                model.goToRegistrarseComplete()
            }
        }

        model.visitante.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.nav_Restaurantes)
                model.goToVisitanteComplete()
            }
        }
    }

}