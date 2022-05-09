package com.example.u_farm.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.u_farm.R
import com.example.u_farm.databinding.FragmentHomeBinding
import com.example.u_farm.home.solutions.SolutionsActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding:FragmentHomeBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)


        (activity as AppCompatActivity).supportActionBar?.hide()
        val application= requireNotNull(this.activity).application
        val viewModelFactory=HomeViewModelFactory(application)
        val homeViewModel= ViewModelProvider(this,viewModelFactory).get(HomeViewModel::class.java)

        binding.homeViewModel=homeViewModel

        binding.lifecycleOwner=this

           val adapter=U_FarmAdapter()
               binding.recyclerView.adapter=adapter

        homeViewModel.allData.observe(viewLifecycleOwner, Observer {
            Log.d("Dhanush","${it.size}")
                     it?.let{
                         adapter.submitList(it)
                     }
        })

        homeViewModel.navigateToSolutionsPage.observe(viewLifecycleOwner, Observer {
            if(it){
                val intent= Intent(application, SolutionsActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                homeViewModel.navigateToSolutionsPageDone()
            }
        })
        return binding.root
    }
}




