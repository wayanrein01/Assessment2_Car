package org.d3if2085.assessment2.ui.car.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.d3if2085.assessment2.R
import org.d3if2085.assessment2.database.RoomDb
import org.d3if2085.assessment2.database.entity.Car
import org.d3if2085.assessment2.databinding.FragmentDetailBinding
import org.d3if2085.assessment2.repository.CarRepository
import org.d3if2085.assessment2.ui.home.HomeFragmentArgs
import org.d3if2085.assessment2.viewmodel.CarViewModel
import org.d3if2085.assessment2.viewmodel.factory.CarViewModelFactory

class DetailFragment : Fragment() {

    private var carId: Long = -1
    private lateinit var binding: FragmentDetailBinding
    private lateinit var car: Car
    private val viewModel: CarViewModel by lazy {
        val db = RoomDb.getInstance(requireContext())
        val repo = CarRepository(db.carDao)
        val factory = CarViewModelFactory(repo)
        ViewModelProvider(this, factory)[CarViewModel::class.java]
    }
    private val args: HomeFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        carId = args.carId
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        initButton()
    }

    private fun initViewModel() {
        viewModel.getOneCar(carId).observe(requireActivity()) {
            if (it == null) return@observe

            car = it
            val carTypeArray: Array<String> = resources.getStringArray(R.array.car_type)
            binding.edtEditCarName.setText(it.name)
            binding.edtEditCarPrice.setText(it.price.toString())
            binding.spinnerEditCarType.setSelection(carTypeArray.indexOf(carTypeArray.first { elem -> elem == it.type }))
        }
    }

    private fun initButton() {
        binding.btnShare.setOnClickListener { shareCar() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.car_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_hapus) {
            hapusCar()
            return true

        }
        return super.onOptionsItemSelected(item)
    }

    private fun hapusCar() {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(R.string.konfirmasi_hapus)
            .setPositiveButton(getString(R.string.hapus)) { _, _ ->
                viewModel.deleteCar(car)
                findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
            }
            .setNegativeButton(getString(R.string.batal)) { dialog, _ ->
                dialog.cancel()
            }
            .show()
    }

    private fun shareCar() {
        val message = getString(
            R.string.bagikan_template,
            car.name,
            car.type,
            car.price.toString(),
        )

        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT, message)
        if (shareIntent.resolveActivity(
                requireActivity().packageManager
            ) != null
        ) {
            startActivity(shareIntent)
        }
    }
}