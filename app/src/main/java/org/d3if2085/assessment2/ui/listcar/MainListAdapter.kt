package org.d3if2085.assessment2.ui.listcar

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.d3if2085.assessment2.R
import org.d3if2085.assessment2.databinding.ListItemBinding
import org.d3if2085.assessment2.model.Mobil
import org.d3if2085.assessment2.network.MobilApi

class MainListAdapter : RecyclerView.Adapter<MainListAdapter.ViewHolder>() {
    private val data = mutableListOf<Mobil>()
    fun updateData(newData: List<Mobil>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }
    class ViewHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mobil: Mobil) = with(binding) {
            namaTextView.text = mobil.namaMobil
            isiTextView.text = mobil.jenisMobil
            Glide.with(imageView.context)
                .load(MobilApi.getMobilUrl(mobil.gambar))
                .error(R.drawable.baseline_broken_image_24)
                .into(imageView)

            root.setOnClickListener {
                Toast.makeText(root.context, mobil.namaMobil, Toast.LENGTH_LONG).show()
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return data.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])

    }

}
