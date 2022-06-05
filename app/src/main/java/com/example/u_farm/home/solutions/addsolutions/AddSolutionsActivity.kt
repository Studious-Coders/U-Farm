 package com.example.u_farm.home.solutions.addsolutions

//import com.example.u_farm.home.addproblems.chosedlang

import android.app.Application
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.u_farm.R
import com.example.u_farm.databinding.AddSolutionsBinding
import com.example.u_farm.home.solutions.SolutionsActivity
import com.example.u_farm.model.Solution
import kotlinx.android.synthetic.main.add_solutions.*
import kotlinx.android.synthetic.main.fragment_profile.*


 class AddSolutionsActivity : AppCompatActivity() {
    private var solution: Solution = Solution()
    private lateinit var progressBar: ProgressDialog
    private lateinit var addSolutionsViewModel: AddSolutionsViewModel
    private lateinit var problem: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<AddSolutionsBinding>(
            this,
            R.layout.add_solutions
        )
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#C4C4C4")))
        progressBar = ProgressDialog(this)
        supportActionBar?.setTitle(R.string.add_solutions)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        problem= intent.getStringExtra("problemUid").toString()

        val application: Application = requireNotNull(this).application
        val viewModelFactory = AddSolutionsViewModelFactory(application, problem)
        addSolutionsViewModel = ViewModelProvider(this, viewModelFactory).get(AddSolutionsViewModel::class.java)
        binding.addSolutionsViewModel = addSolutionsViewModel
        binding.solution=solution
        binding.lifecycleOwner = this

        addSolutionsViewModel.setData.observe(this, Observer {
            if(it==true){
                Toast.makeText(this,"Added Solution",Toast.LENGTH_LONG).show()
                progressBar.dismiss()
               }
        })

        //Speech ToText
        val startForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val spokenText: String? =
                    result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                        .let { text -> text?.get(0) }
                binding.convertText1.setText(spokenText)
               }
        }

        addSolutionsViewModel.get.observe(this,Observer{
            finish()
        })
        addSolutionsViewModel.initial(startForResult)
        mic.setOnClickListener {
            addSolutionsViewModel.startRecording()
        }
    }

     override fun onBackPressed() {
         super.onBackPressed()
         startActivity(Intent(this,SolutionsActivity::class.java))
     }
 }
