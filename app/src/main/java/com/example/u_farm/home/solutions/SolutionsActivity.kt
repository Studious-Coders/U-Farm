package com.example.u_farm.home.solutions

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.u_farm.R
import com.example.u_farm.databinding.ActivityAddProblemsBinding
import com.example.u_farm.databinding.ActivitySolutionsBinding
import com.example.u_farm.home.solutions.addsolutions.AddSolutionsActivity

class SolutionsActivity : AppCompatActivity() {
    private lateinit var solutionsViewModel: SolutionsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySolutionsBinding>(
            this,
            R.layout.activity_solutions
        )
        supportActionBar?.hide()
        val application: Application = requireNotNull(this).application
        val activity: Activity =this
        val viewModelFactory = SolutionsViewModelFactory(application,activity)
        solutionsViewModel =
            ViewModelProvider(this, viewModelFactory).get(SolutionsViewModel::class.java)
        binding.solutionsViewModel=solutionsViewModel

        binding.lifecycleOwner = this

       solutionsViewModel.navigateToAddSolutions.observe(this, Observer {
           if(it) {
               val intent = Intent(this, AddSolutionsActivity::class.java)
               startActivity(intent)
               solutionsViewModel.navigateToAddSolutionsDone()
           }
       })




    }
}