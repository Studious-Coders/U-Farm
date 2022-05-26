package com.example.u_farm.home.solutions.addsolutions

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.result.contract.ActivityResultContracts
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.u_farm.R
import com.example.u_farm.databinding.ActivityAddSolutionsBinding
import kotlinx.android.synthetic.main.activity_add_problems.*
import kotlinx.android.synthetic.main.activity_add_solutions.*
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

        val startForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val spokenText: String? =
                    result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                        .let { text -> text?.get(0) }
                binding.convertText1.setText(spokenText)
                addSolutionsViewModel.editDone()
            }
        }

        addSolutionsViewModel.initial(startForResult)
//        with(binding) {
        mic.setOnClickListener { addSolutionsViewModel.startRecording() }
//            fabPlay.setOnClickListener {
//                val text = edtText.text?.trim().toString()
//                model.speak(if (text.isNotEmpty()) text else "Text tidak boleh kosong")
//            }
//        }



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