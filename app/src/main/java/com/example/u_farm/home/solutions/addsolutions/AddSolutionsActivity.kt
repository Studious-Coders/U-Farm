package com.example.u_farm.home.solutions.addsolutions

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.u_farm.R
import com.example.u_farm.databinding.ActivityAddSolutionsBinding
import com.example.u_farm.home.solutions.SolutionsActivity.Companion.USER_KEY

class AddSolutionsActivity : AppCompatActivity() {
    private lateinit var problem: String
    private lateinit var addSolutionsViewModel: AddSolutionsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityAddSolutionsBinding>(
            this,
            R.layout.activity_add_solutions
        )
        supportActionBar?.title="Add Problems"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#C4C4C4")))
        problem= intent.getStringExtra(USER_KEY).toString()



        val application: Application = requireNotNull(this).application
        val activity: Activity =this
        val viewModelFactory = AddSolutionsViewModelFactory(application,activity)
        addSolutionsViewModel =
            ViewModelProvider(this, viewModelFactory).get(AddSolutionsViewModel::class.java)
        binding.addSolutionsViewModel=addSolutionsViewModel

        binding.lifecycleOwner = this


        addSolutionsViewModel.spinner.observe(this,Observer{
            if(it==true){
                Log.d("Dhanush","$problem")
                addSolutionsViewModel.arguments(problem)
                addSolutionsViewModel.argumentsPassed()
            }
        })

        addSolutionsViewModel.setData.observe(this,Observer{
            if(it==null){
                Toast.makeText(this,"Your Solution is Added", Toast.LENGTH_LONG).show()

            }
        })





    }
}