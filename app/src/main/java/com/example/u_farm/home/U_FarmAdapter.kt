package com.example.u_farm.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.u_farm.R
import com.example.u_farm.databinding.FragmentHomeBinding
import com.example.u_farm.model.U_Farm
import de.hdodenhof.circleimageview.CircleImageView

class U_FarmAdapter : RecyclerView.Adapter<U_FarmAdapter.ViewHolder>() {

    var data = listOf<U_Farm>()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.username.text = item.username

        holder.photo.setImageResource(R.drawable.plantdiseases)
        holder.description.setText(R.string.problem1)
        holder.dp.setImageResource(R.drawable.plantdiseases)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater
            .inflate(R.layout.fragment_home, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val username: TextView = itemView.findViewById(R.id.username)
        val photo: ImageView = itemView.findViewById(R.id.photo)
        val description: TextView = itemView.findViewById(R.id.description)
        val dp: CircleImageView = itemView.findViewById(R.id.selectphoto1)
    }

}

//class U_FarmAdapter : ListAdapter<U_Farm,
//        U_FarmAdapter.ViewHolder>(U_FarmDiffCallback()) {
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = getItem(position)
//        holder.bind(item)
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        return ViewHolder.from(parent)
//    }
//
//    class ViewHolder private constructor(val binding: FragmentHomeBinding)
//        : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(item: U_Farm) {
//
//            binding.username.text = item.username
//            binding.selectphoto1.setImageResource(R.drawable.plantdiseases)
//            binding.description.setText(R.string.problem1)
//            binding.selectphoto1.setImageResource(R.drawable.plantdiseases)
//           // binding.executePendingBindings()
//        }
//
//        companion object {
//            fun from(parent: ViewGroup): ViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val binding = FragmentHomeBinding.inflate(layoutInflater, parent, false)
//                return ViewHolder(binding)
//            }
//        }
//    }
//}
//class U_FarmDiffCallback : DiffUtil.ItemCallback<U_Farm>() {
//            override fun areItemsTheSame(oldItem: U_Farm, newItem: U_Farm): Boolean {
//                return oldItem.username == newItem.username
//            }
//
//            override fun areContentsTheSame(oldItem: U_Farm, newItem: U_Farm): Boolean {
//                return oldItem== newItem
//            }
//        }