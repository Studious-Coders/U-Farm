package com.example.u_farm.home.solutions.addsolutions

import android.app.Activity
import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.u_farm.R
import com.example.u_farm.databinding.ActivityAddSolutionsBinding
import kotlinx.android.synthetic.main.activity_add_problems.*
import kotlinx.android.synthetic.main.activity_add_solutions.*

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








    }
}