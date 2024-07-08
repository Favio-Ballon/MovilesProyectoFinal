package com.example.movilesproyectofinal.ui.fragment

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.movilesproyectofinal.R
import com.example.movilesproyectofinal.databinding.FragmentSubirImagenBinding
import com.example.movilesproyectofinal.repositories.PreferencesRepository
import com.example.movilesproyectofinal.ui.viewmodel.SubirImagenViewModel
import java.io.IOException


class SubirImagenFragment : Fragment() {

    private val model: SubirImagenViewModel by viewModels()
    private lateinit var binding: FragmentSubirImagenBinding

    private val PICK_IMAGE_REQUEST = 1
    private val STORAGE_PERMISSION_CODE = 101

    private var id : Long ?= null
    private var isLogo : Boolean ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSubirImagenBinding.inflate(inflater, container, false)

        setUpEventListeners()
        setUpViewModelObservers()


        /*
        bundle.putLong("id", id!!)
            bundle.putBoolean("isLogo", true)
         */
        id = arguments?.getLong("id")
        isLogo = arguments?.getBoolean("isLogo")

        Glide.with(this)
            .load(R.drawable.loading)
            .into(binding.imgLoading)


        return binding.root
    }


    fun setUpEventListeners() {
        binding.btnChooseImage.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openImageChooser()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    STORAGE_PERMISSION_CODE
                )
            }
        }

        binding.btnUploadImage.setOnClickListener {
            if (isLogo!!) {
                val imagen = model.selectedImageBitmap.value
                model.subirImagen(id!!, true, imagen!!, PreferencesRepository.getToken(requireContext()))
            }else{
                val imagen = model.selectedImageBitmap.value
                model.subirImagen(id!!, false, imagen!!, PreferencesRepository.getToken(requireContext()))

            }
        }
    }

    fun setUpViewModelObservers() {
        model.selectedImageBitmap.observe(viewLifecycleOwner){
            if (it != null) {
                binding.imgSelected.setImageBitmap(it)
            }
        }
        model.errorMessage.observe(viewLifecycleOwner) {
            if (it != "") {
                Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            }
        }

        model.showLoading.observe(viewLifecycleOwner) {
            if (it) {
                binding.imgLoading.visibility = View.VISIBLE
            } else {
                binding.imgLoading.visibility = View.GONE
            }
        }
        model.subirImagen.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Imagen subida correctamente", Toast.LENGTH_LONG).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun openImageChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Selecciona una imagen"),
            PICK_IMAGE_REQUEST
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val filePath: Uri? = data.data
            try {
                val bitmap: Bitmap =
                    MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, filePath)
                model.setImageUri(filePath)
                model.setImageBitmap(bitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openImageChooser()
            } else {

            }
        }
    }

}

