package com.example.u_farm.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.u_farm.R
import com.example.u_farm.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : Fragment() {

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding:FragmentHomeBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_home,container,false)

        setHasOptionsMenu(true)
        val application= requireNotNull(this.activity).application



        val viewModelFactory=HomeViewModelFactory(application)
        val homeViewModel= ViewModelProvider(this,viewModelFactory).get(HomeViewModel::class.java)

        binding.homeViewModel=homeViewModel

        binding.lifecycleOwner=this

        //Initialize the adapter onClick event happen on each object (lamba function)
        val adapter=ProblemsAdapter(ProblemsListener { uid ->
            homeViewModel.navigateToSolutionsPage(uid)

        })


        binding.recyclerView.adapter=adapter

        homeViewModel.allData.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                loading_spinner2.visibility=View.GONE
                Log.d("pp", it.toString())


                    adapter.submitList(it)
                    adapter.notifyDataSetChanged()

            }
        })

        homeViewModel.navigateToSolutionsPage.observe(viewLifecycleOwner, Observer {
            if(it!=""){
                this.findNavController().navigate(HomeFragmentDirections.actionHomeToSolutionsActivity(it))
                homeViewModel.navigateToSolutionsPageDone()
            }
        })

        homeViewModel.navigateToAddProblemsPage.observe(viewLifecycleOwner, Observer {
            if(FirebaseAuth.getInstance().uid!=null){
                if(it==true){
                    this.findNavController().navigate(HomeFragmentDirections.actionHomeToAddProblemsActivity())
                    homeViewModel.navigateToAddProblemsPageDone()
                }
            }else{
                this.findNavController().navigate(HomeFragmentDirections.actionHomeToLoginActivity())
            }

        })

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.search -> {
                return true
            }
            R.id.image -> {
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }
    }

}




