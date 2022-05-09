package com.example.u_farm.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.u_farm.R
import com.example.u_farm.databinding.ListItemsBinding
import com.example.u_farm.model.U_Farm

class U_FarmAdapter : ListAdapter<U_Farm,U_FarmAdapter.ViewHolder>(U_FarmDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemsBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: U_Farm) {
           binding.data=item
            binding.username.text=item.username
            binding.photo.setImageResource(R.drawable.plantdiseases)
            binding.selectphoto1.setImageResource(R.drawable.plantdiseases)
            binding.description.setText(R.string.problem1)
           binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}
class U_FarmDiffCallback : DiffUtil.ItemCallback<U_Farm>() {
            override fun areItemsTheSame(oldItem: U_Farm, newItem: U_Farm): Boolean {
                return oldItem.username == newItem.username
            }

            override fun areContentsTheSame(oldItem: U_Farm, newItem: U_Farm): Boolean {
                return oldItem== newItem
            }
        }