package org.d3if2085.assessment2.ui.car.add

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import org.d3if2085.assessment2.R
import org.d3if2085.assessment2.database.RoomDb
import org.d3if2085.assessment2.database.entity.Car
import org.d3if2085.assessment2.databinding.FragmentAddBinding
import org.d3if2085.assessment2.repository.CarRepository
import org.d3if2085.assessment2.viewmodel.CarViewModel
import org.d3if2085.assessment2.viewmodel.factory.CarViewModelFactory

class AddFragment : Fragment() {

    private lateinit var binding: FragmentAddBinding
    private val viewModel: CarViewModel by lazy {
        val db = RoomDb.getInstance(requireContext())
        val repo = CarRepository(db.carDao)
        val factory = CarViewModelFactory(repo)
        ViewModelProvider(this, factory)[CarViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnAddCar.setOnClickListener {
            addCar()
        }
    }

    private fun checkInput(name: String, type: String, price: String): Boolean {
        return (TextUtils.isEmpty(name) || TextUtils.isEmpty(type) || TextUtils.isEmpty(price))
    }

    private fun addCar() {
        val name = binding.edtCarName.text.toString()
        val type = binding.spinnerTypeCar.selectedItem.toString()
        val price = binding.edtPriceCar.text.toString()

        if (checkInput(name, type, price)) {
            Toast.makeText(requireContext(), "Is required", Toast.LENGTH_LONG).show()
            return
        }

        val car = Car(0L, name, type, price.toFloat())
        viewModel.insertCar(car)
        Toast.makeText(requireContext(), "Car is added", Toast.LENGTH_LONG).show()
        findNavController().navigate(R.id.action_addFragment_to_homeFragment)
    }
}