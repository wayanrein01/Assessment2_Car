package org.d3if2085.assessment2.ui.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.d3if2085.assessment2.database.entity.Car
import org.d3if2085.assessment2.databinding.ItemCarBinding
import org.d3if2085.assessment2.ui.home.HomeFragmentDirections


class CarAdapter : ListAdapter<Car, CarAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<Car>() {
                override fun areItemsTheSame(
                    oldItem: Car,
                    newItem: Car
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: Car,
                    newItem: Car
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    class ViewHolder(private val binding: ItemCarBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(car: Car) = with(binding) {
            tvCarName.text = car.name
            tvCarType.text = car.type
            tvCarPrice.text = car.price.toString()

            val direction =
                HomeFragmentDirections.actionHomeFragmentToDetailFragment(carId = car.id)
            root.setOnClickListener {
                it.findNavController()
                    .navigate(direction)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarAdapter.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCarBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}