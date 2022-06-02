package com.example.u_farm.home.solutions.comment

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.u_farm.R
import com.example.u_farm.databinding.ActivityCommentsBinding
import com.example.u_farm.home.solutions.addsolutions.AddSolutionsViewModel
import com.example.u_farm.home.solutions.addsolutions.AddSolutionsViewModelFactory

class CommentsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityCommentsBinding>(
            this,
            R.layout.activity_comments
        )
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#C4C4C4")))


//        progressBar = ProgressDialog(this)
       supportActionBar?.title = "Comments"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


//        problem= intent.getStringExtra("solutionUid").toString()

        val application: Application = requireNotNull(this).application
        val activity: Activity = this
        val viewModelFactory = AddSolutionsViewModelFactory(application, activity)
//        addSolutionsViewModel =
//            ViewModelProvider(this, viewModelFactory).get(AddSolutionsViewModel::class.java)
//        binding.addSolutionsViewModel = addSolutionsViewModel
//        binding.solution=solution
//        binding.lifecycleOwner = this

    }
}
