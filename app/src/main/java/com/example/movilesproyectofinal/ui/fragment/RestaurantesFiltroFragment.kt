package com.example.movilesproyectofinal.ui.fragment

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.movilesproyectofinal.R
import com.example.movilesproyectofinal.databinding.FragmentRestaurantesFiltroBinding
import com.example.movilesproyectofinal.ui.activities.MainActivity
import java.util.Calendar


class RestaurantesFiltroFragment : Fragment() {

    private lateinit var binding: FragmentRestaurantesFiltroBinding
    val calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as MainActivity).binding.appBarRestaurantes.fab.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentRestaurantesFiltroBinding.inflate(inflater, container, false)
        setButtonListeners()

        // Inflate the layout for this fragment
        return binding.root
    }

    fun setButtonListeners() {
        binding.buttonSelectDate.setOnClickListener {
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(requireContext(), { _, selectedYear, selectedMonth, selectedDay ->
                binding.buttonSelectDate.text = "$selectedYear-${selectedMonth + 1}-$selectedDay"
            }, year, month, day)

            datePickerDialog.show()
        }

        binding.buttonSelectTime.setOnClickListener {
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(requireContext(), { _, selectedHour, selectedMinute ->
                binding.buttonSelectTime.text = String.format("%02d:%02d", selectedHour, selectedMinute)
            }, hour, minute, true)

            timePickerDialog.show()
        }

        binding.buttonSearch.setOnClickListener {
            var date = binding.buttonSelectDate.text
            var time = binding.buttonSelectTime.text
            val city = binding.editTextCity.text.toString().trim()

            if ((date == "Seleccionar Fecha" && time != "Seleccionar Hora") || (date != "Seleccionar Fecha" && time == "Seleccionar Hora")){
                Toast.makeText(requireContext(), "Por favor, selecciona fecha y hora", Toast.LENGTH_SHORT).show()
            }else if (date == "Seleccionar Fecha" && time == "Seleccionar Hora" && city.isEmpty()){
                Toast.makeText(
                    requireContext(),
                    "Por favor, selecciona fecha, hora o ciudad",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                if(!time.toString().contains(":")){
                    time = ""
                }

                if(!date.toString().contains("-")){
                    date = ""
                }

                // send data to RestaurantesFragment
                val bundle = Bundle()
                bundle.putString("date", date.toString())
                bundle.putString("time", time.toString())
                bundle.putString("city", city)
                val restaurantesFragment = RestaurantesFragment()
                restaurantesFragment.arguments = bundle
                findNavController().navigate(R.id.nav_Restaurantes, bundle)
            }
        }

    }
}