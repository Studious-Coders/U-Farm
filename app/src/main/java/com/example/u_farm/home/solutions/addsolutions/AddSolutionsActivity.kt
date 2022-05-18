package com.example.u_farm.home.solutions.addsolutions

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.u_farm.R
import com.example.u_farm.databinding.ActivityAddSolutionsBinding

class AddSolutionsActivity : AppCompatActivity() {
    private lateinit var addSolutionsViewModel: AddSolutionsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityAddSolutionsBinding>(
            this,
            R.layout.activity_add_solutions
        )
        supportActionBar?.title="Add Problems"

        val application: Application = requireNotNull(this).application
        val activity: Activity =this
        val viewModelFactory = AddSolutionsViewModelFactory(application,activity)
        addSolutionsViewModel =
            ViewModelProvider(this, viewModelFactory).get(AddSolutionsViewModel::class.java)
        binding.addSolutionsViewModel=addSolutionsViewModel

        binding.lifecycleOwner = this







    }
}