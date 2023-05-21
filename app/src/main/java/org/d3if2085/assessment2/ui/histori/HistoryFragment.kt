package org.d3if2085.assessment2.ui.histori

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import org.d3if2085.assessment2.R
import org.d3if2085.assessment2.database.RoomDb
import org.d3if2085.assessment2.database.entity.Car
import org.d3if2085.assessment2.databinding.FragmentHistoriBinding
import org.d3if2085.assessment2.repository.CarRepository
import org.d3if2085.assessment2.ui.adapter.CarAdapter
import org.d3if2085.assessment2.ui.home.HomeFragmentArgs
import org.d3if2085.assessment2.viewmodel.CarViewModel
import org.d3if2085.assessment2.viewmodel.factory.CarViewModelFactory

class HistoriFragment : Fragment() {
    private lateinit var carAdapter: CarAdapter
    private val viewModel: CarViewModel by lazy {
        val db = RoomDb.getInstance(requireContext())
        val repo = CarRepository(db.carDao)
        val factory = CarViewModelFactory(repo)
        ViewModelProvider(this, factory)[CarViewModel::class.java]
    }
    private lateinit var binding: FragmentHistoriBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoriBinding.inflate(
            layoutInflater,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initCarAdapter()
    }

    private fun initCarAdapter() {
        carAdapter = CarAdapter()
        with(binding.recyclerView) {
            adapter = carAdapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.getAllCar.observe(viewLifecycleOwner) {
            binding.emptyView.visibility = if (it.isEmpty()) View.VISIBLE else View.GONE
            carAdapter.submitList(it)
        }
    }


}