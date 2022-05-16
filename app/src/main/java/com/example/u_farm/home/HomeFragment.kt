package com.example.u_farm.home

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
import com.example.u_farm.R
import com.example.u_farm.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth

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

           //val adapter=U_FarmAdapter()
        //Initialize the adapter onClick event happen on each object (lamba function)
        val adapter=ProblemsAdapter(ProblemsListener { username ->
            homeViewModel.navigateToSolutionsPage(username)

        })

        binding.recyclerView.adapter=adapter

        homeViewModel.allData.observe(viewLifecycleOwner, Observer {
                                 it?.let{
                         adapter.submitList(it)
                     }
        })


        homeViewModel.navigateToSolutionsPage.observe(viewLifecycleOwner, Observer {
            if(it!=""){
                this.findNavController().navigate(HomeFragmentDirections.actionHomeToSolutionsActivity())
                homeViewModel.navigateToSolutionsPageDone()
      }
        })

        homeViewModel.navigateToAddProblemsPage.observe(viewLifecycleOwner, Observer {

            if(FirebaseAuth.getInstance().uid!=null){
                if(it){
                    this.findNavController().navigate(HomeFragmentDirections.actionHomeToAddProblemsActivity())
                    homeViewModel.navigateToAddProblemsPageDone()
                }
            }else{
               this.findNavController().navigate(HomeFragmentDirections.actionHomeToLoginActivity())
            }

        })

        return binding.root
    }
}




