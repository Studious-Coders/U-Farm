 package com.example.u_farm.home.solutions.addsolutions

import android.app.Activity
import android.app.Application
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import androidx.navigation.navArgument
import com.example.u_farm.R
import com.example.u_farm.databinding.AddSolutionsBinding
import com.example.u_farm.home.solutions.SolutionsActivity
import com.example.u_farm.home.solutions.SolutionsActivity.Companion.USER_KEY
import com.example.u_farm.home.solutions.SolutionsActivityArgs
import com.example.u_farm.model.Solution

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
        supportActionBar?.title = "Add Problems"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        problem= intent.getStringExtra("problemUid").toString()

        val application: Application = requireNotNull(this).application
        val activity: Activity = this
        val viewModelFactory = AddSolutionsViewModelFactory(application, activity)
        addSolutionsViewModel =
            ViewModelProvider(this, viewModelFactory).get(AddSolutionsViewModel::class.java)
        binding.addSolutionsViewModel = addSolutionsViewModel
        binding.solution=solution
        binding.lifecycleOwner = this

        addSolutionsViewModel.initial.observe(this, Observer {
            if(it==true){
              addSolutionsViewModel.passArguments(problem)

            }
        })


        addSolutionsViewModel.setData.observe(this, Observer {
            if(it==true){
                Toast.makeText(this,"Added Solution",Toast.LENGTH_LONG).show()
                progressBar.dismiss()


            }
        })


    }


}


//            addProblemsViewModel.setImage.observe(this, Observer {
//            if(it!=null){
//                loading_spinner2.visibility= View.GONE
//             }
//        })
//
//        addProblemsViewModel.spinner.observe(this, Observer {
//            if(it==true){
//                loading_spinner2.visibility= View.VISIBLE
//            }
//        })
//
//        floating_action_button.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
//            when (motionEvent.action) {
//                MotionEvent.ACTION_DOWN -> {
//                    addProblemsViewModel.stopRecording()
//                }
//            }
//        }

//        val textToSpeechEngine: TextToSpeech by lazy {
//            TextToSpeech(this) {
//                if (it == TextToSpeech.SUCCESS) textToSpeechEngine.language = Locale("in_ID")
//            }
//        }



//        with(binding) {
//            fabPlay.setOnClickListener {
//                val text = edtText.text?.trim().toString()
//                model.speak(if (text.isNotEmpty()) text else "Text tidak boleh kosong")
//            }
//        }
//        addProblemsViewModel.edit.observe(this, Observer {
//            if(it==true){
//             addProblemsViewModel.str= convertText1.text.toString()
//                addProblemsViewModel.editDone()
//            }
//        })


//        floating_action_button.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
//            when (motionEvent.action){
//                MotionEvent.ACTION_DOWN -> {
//                  addProblemsViewModel.stopRecording()
//
//                }
//                MotionEvent.ACTION_UP -> {
//
//                    addProblemsViewModel.startRecording()
//                }
//            }
//            return@OnTouchListener false
//        })
