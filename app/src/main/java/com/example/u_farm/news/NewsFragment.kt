package com.example.u_farm.news

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.u_farm.R
import com.example.u_farm.databinding.FragmentHomeBinding
import com.example.u_farm.databinding.FragmentNewsBinding
import com.example.u_farm.home.NewsAdapter
import com.example.u_farm.home.ProblemsAdapter
import com.example.u_farm.home.ProblemsListener
import com.example.u_farm.news.NewsViewModel
import com.example.u_farm.news.NewsViewModelFactory
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_home.*

class NewsFragment : Fragment() {

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding:FragmentNewsBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_news,container,false)


        val application= requireNotNull(this.activity).application

//        https://newsdata.io/api/1/news?apikey=pub_213032277d5fc9a185d8fd3657596d0d7ff73&q=agri&q=india

        val viewModelFactory= NewsViewModelFactory(application)

        val newsViewModel= ViewModelProvider(this,viewModelFactory).get(NewsViewModel::class.java)

        binding.newsViewModel=newsViewModel

        binding.lifecycleOwner=this


       val adapter= NewsAdapter()

        binding.recyclerView5.adapter=adapter
       newsViewModel.newsFeed.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                loading_spinner2.visibility=View.GONE
                Log.d("sum", it.toString())


                adapter.submitList(it)
                adapter.notifyDataSetChanged()

            }
        })

        return binding.root

    }

}




