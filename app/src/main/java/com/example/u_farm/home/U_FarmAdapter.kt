package com.example.u_farm.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.u_farm.databinding.ListItems2Binding
import com.example.u_farm.databinding.ListItems3Binding
import com.example.u_farm.databinding.ListItemsBinding
import com.example.u_farm.model.Comments
import com.example.u_farm.model.Problem
import com.example.u_farm.model.Solution

class ProblemsAdapter(val clickListener:ProblemsListener) : ListAdapter<Problem,ProblemsAdapter.ViewHolder>(ProblemsDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItemsBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Problem,clickListener: ProblemsListener) {
              binding.data=item
            binding.clicklistener=clickListener
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
    fun onClick(model: Problem)=clickListener(model.problemUid)
}
class ProblemsDiffCallback : DiffUtil.ItemCallback<Problem>() {
            override fun areItemsTheSame(oldItem: Problem, newItem: Problem): Boolean {
                return oldItem.username == newItem.username
            }

            override fun areContentsTheSame(oldItem: Problem, newItem: Problem): Boolean {
                return oldItem== newItem
            }
        }

class SolutionsAdapter(val solutionsListener1: SolutionsListener1,val clickListener: SolutionsListener,val increaseListener: IncreaseListener,val decreaseListener: DecreaseListener) : ListAdapter<Solution,SolutionsAdapter.ViewHolder>(SolutionsDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,solutionsListener1,clickListener,increaseListener,decreaseListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItems2Binding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Solution,solutionsListener1: SolutionsListener1,clickListener: SolutionsListener,increaseListener: IncreaseListener,decreaseListener: DecreaseListener) {
            binding.data=item
            binding.solutionListener1=solutionsListener1
            binding.clicklistener=clickListener
            binding.increaseListener=increaseListener
            binding.decreaseListener=decreaseListener
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
class SolutionsListener1(val clickListener: (sleepId: String)-> Unit){
    fun onClick(model:Solution)=clickListener(model.solutionUid)
}

class SolutionsListener(val clickListener: (sleepId: String)-> Unit){
    fun onClick(model:Solution)=clickListener(model.solutionStatement)
}

class IncreaseListener(val clickListener: (sleepId:Int,sleepId2:String) -> Unit){
    fun onClick(model:Solution)=clickListener(model.rating,model.solutionUid)
}

class DecreaseListener(val clickListener: (sleepId:Int,sleepId2:String) -> Unit){
    fun onClick(model:Solution)=clickListener(model.rating,model.solutionUid)
}




class SolutionsDiffCallback : DiffUtil.ItemCallback<Solution>() {
    override fun areItemsTheSame(oldItem: Solution, newItem: Solution): Boolean {
        return oldItem.username == newItem.username
    }

    override fun areContentsTheSame(oldItem: Solution, newItem: Solution): Boolean {
        return oldItem== newItem
    }
}

class CommentsAdapter(val clickListener:CommentsListener) : ListAdapter<Comments,CommentsAdapter.ViewHolder>(CommentsDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: ListItems3Binding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Comments, clickListener: CommentsListener) {
            binding.data=item
            binding.clicklistener=clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItems3Binding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }
}

class CommentsListener(val clickListener: (sleepId:String) -> Unit){
    fun onClick(model: Comments)=clickListener(model.commentStatement)
}
class CommentsDiffCallback : DiffUtil.ItemCallback<Comments>() {
    override fun areItemsTheSame(oldItem: Comments, newItem: Comments): Boolean {
        return oldItem.username == newItem.username
    }

    override fun areContentsTheSame(oldItem: Comments, newItem: Comments): Boolean {
        return oldItem== newItem
    }
}

