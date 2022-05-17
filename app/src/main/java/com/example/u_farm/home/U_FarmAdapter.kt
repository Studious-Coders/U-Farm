package com.example.u_farm.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.u_farm.R
import com.example.u_farm.databinding.ListItems2Binding
import com.example.u_farm.databinding.ListItemsBinding
import com.example.u_farm.model.U_Farm

class ProblemsAdapter(val clickListener:ProblemsListener) : ListAdapter<U_Farm,ProblemsAdapter.ViewHolder>(ProblemsDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemsBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: U_Farm,clickListener: ProblemsListener) {
       binding.data=item
            binding.clicklistener=clickListener
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

class ProblemsListener(val clickListener: (sleepId:String) -> Unit){
    fun onClick(model:U_Farm)=clickListener(model.username)



}
class ProblemsDiffCallback : DiffUtil.ItemCallback<U_Farm>() {
            override fun areItemsTheSame(oldItem: U_Farm, newItem: U_Farm): Boolean {
                return oldItem.username == newItem.username
            }

            override fun areContentsTheSame(oldItem: U_Farm, newItem: U_Farm): Boolean {
                return oldItem== newItem
            }
        }

class SolutionsAdapter() : ListAdapter<U_Farm,SolutionsAdapter.ViewHolder>(U_FarmDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItems2Binding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: U_Farm) {
            binding.data=item
            binding.username1.text=item.username
            binding.selectphoto5.setImageResource(R.drawable.plantdiseases)
            binding.solutionstatement.setText(R.string.solution1)
//            binding.clicklistener=clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItems2Binding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

//class SolutionsListener(val clickListener: (sleepId:String) -> Unit){
//    fun onClick(model:U_Farm)=clickListener(model.username)
//
//
//
//}


class U_FarmDiffCallback : DiffUtil.ItemCallback<U_Farm>() {
    override fun areItemsTheSame(oldItem: U_Farm, newItem: U_Farm): Boolean {
        return oldItem.username == newItem.username
    }

    override fun areContentsTheSame(oldItem: U_Farm, newItem: U_Farm): Boolean {
        return oldItem== newItem
    }
}