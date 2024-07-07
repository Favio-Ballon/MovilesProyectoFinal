package com.example.movilesproyectofinal.ui.fragment


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels

import com.example.movilesproyectofinal.databinding.FragmentFullScreenImageBinding
import com.example.movilesproyectofinal.ui.viewmodel.FullScreenImageViewModel

private const val ARG_PARAM1 = "param1"


class FullScreenImageFragment : Fragment() {
    private var urlImage: String? = null
    lateinit var binding: FragmentFullScreenImageBinding
    private val model: FullScreenImageViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            urlImage = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFullScreenImageBinding.inflate(inflater, container, false)
        setupViewModelObservers()
        setupListeners()
        urlImage?.let { loadImage(it) }
        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment FullScreenImageFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            FullScreenImageFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    private fun setupViewModelObservers() {
        model.closeActivity.observe(viewLifecycleOwner) {
            if (it) {
                parentFragmentManager.popBackStack()
            }
        }
        model.showLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.imgLoading.visibility = View.VISIBLE
            } else {
                binding.imgLoading.visibility = View.GONE
            }
        }
        model.errorMessage.observe(viewLifecycleOwner) {
            if (it != "") {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setupListeners() {
        binding.returnButton.setOnClickListener {
            model.closeActivity()
        }
    }

    private fun loadImage(urlImage: String) {
        context?.let {
            model.loadImage(urlImage,
                it,
                onSuccess = { bitmap ->
                    binding.imagenFullScreen.setImageBitmap(bitmap)
                },
                onError = { exception ->
                    Log.e("Error", exception.message.toString())
                }
            )
        }
    }


}