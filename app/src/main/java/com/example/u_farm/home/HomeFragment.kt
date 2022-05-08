package com.example.u_farm.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.u_farm.R
import com.example.u_farm.databinding.FragmentHomeBinding
import com.example.u_farm.databinding.RecyclerviewBinding
import com.example.u_farm.home.solutions.SolutionsActivity
import com.example.u_farm.register.RegisterFragmentDirections
import kotlinx.android.synthetic.main.activity_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding:RecyclerviewBinding=DataBindingUtil.inflate(inflater,R.layout.recyclerview,container,false)


        (activity as AppCompatActivity).supportActionBar?.hide()
        val application= requireNotNull(this.activity).application
        val viewModelFactory=HomeViewModelFactory(application)
        val homeViewModel= ViewModelProvider(this,viewModelFactory).get(HomeViewModel::class.java)

        binding.recyclerViewModel=homeViewModel

        binding.lifecycleOwner=this

           val adapter=U_FarmAdapter()
               binding.recyclerView.adapter=adapter

        homeViewModel.navigateToSolutionsPage.observe(viewLifecycleOwner, Observer {
            if(it){
                val intent= Intent(application, SolutionsActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        })
        return binding.root
    }
}




