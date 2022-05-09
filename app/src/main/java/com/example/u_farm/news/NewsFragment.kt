package com.example.u_farm.news

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.u_farm.R

import com.example.u_farm.databinding.FragmentNewsBinding
import kotlinx.android.synthetic.main.activity_home.*

class NewsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentNewsBinding =
            DataBindingUtil.inflate(inflater,R.layout.fragment_news,container,false)
        (activity as AppCompatActivity).supportActionBar?.title="News"

        return binding.root
    }
}