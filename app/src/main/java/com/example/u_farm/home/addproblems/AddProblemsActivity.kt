package com.example.u_farm.home.addproblems

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.u_farm.R
import com.example.u_farm.databinding.ActivityAddProblemsBinding

class AddProblemsActivity : AppCompatActivity() {
    private lateinit var addProblemsViewModel:AddProblemsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityAddProblemsBinding>(
            this,
            R.layout.activity_add_problems
        )
        supportActionBar?.title="Add Problems"

        val application: Application = requireNotNull(this).application
        val activity: Activity =this
        val viewModelFactory = AddProblemsViewModelFactory(application,activity)
        addProblemsViewModel =
            ViewModelProvider(this, viewModelFactory).get(AddProblemsViewModel::class.java)
        binding.addProblemsViewModel=addProblemsViewModel

        binding.lifecycleOwner = this






    }
}