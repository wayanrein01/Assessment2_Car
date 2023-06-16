package org.d3if2085.assessment2.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.d3if2085.assessment2.R
import org.d3if2085.assessment2.database.RoomDb
import org.d3if2085.assessment2.database.entity.Car
import org.d3if2085.assessment2.databinding.FragmentHomeBinding
import org.d3if2085.assessment2.repository.CarRepository
import org.d3if2085.assessment2.ui.adapter.CarAdapter
import org.d3if2085.assessment2.viewmodel.CarViewModel
import org.d3if2085.assessment2.viewmodel.factory.CarViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var carAdapter: CarAdapter
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
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initCarAdapter()
        initButton()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home_menu, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_histori -> {
                findNavController().navigate(R.id.action_homeFragment_to_historiFragment)
                return true
            }
            R.id.menu_list -> {
                findNavController().navigate(R.id.action_homeFragment_to_mainListFragment)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initButton() {
        binding.floatingAdd.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)
        }
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