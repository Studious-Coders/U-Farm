package com.example.u_farm.home.solutions.comment

import android.app.Activity
import android.app.Application
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.example.u_farm.R
import com.example.u_farm.databinding.ActivityCommentsBinding
import com.example.u_farm.home.CommentsAdapter
import com.example.u_farm.home.CommentsListener
import com.example.u_farm.home.solutions.SolutionsActivityArgs
import com.example.u_farm.home.solutions.SolutionsViewModel
import com.example.u_farm.home.solutions.SolutionsViewModelFactory
import com.example.u_farm.home.solutions.addsolutions.AddSolutionsViewModel
import com.example.u_farm.home.solutions.addsolutions.AddSolutionsViewModelFactory
import com.example.u_farm.model.Comments
import kotlinx.android.synthetic.main.activity_add_problems.*

class CommentsActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressDialog
    private lateinit var problem: String
    private lateinit var commentsViewModel: CommentsViewModel
    private var comments:Comments= Comments()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityCommentsBinding>(
            this,
            R.layout.activity_comments
        )
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#C4C4C4")))


        progressBar = ProgressDialog(this)
       supportActionBar?.title = "Comments"

        supportActionBar?.setDisplayHomeAsUpEnabled(false)


        problem= intent.getStringExtra("solutionUid").toString()

        val application: Application = requireNotNull(this).application
        val viewModelFactory = CommentsViewModelFactory(application, problem)
        commentsViewModel = ViewModelProvider(this, viewModelFactory).get(CommentsViewModel::class.java)
        binding.commentsViewModel = commentsViewModel
        binding.comments=comments
        binding.lifecycleOwner = this

        val adapter=CommentsAdapter(CommentsListener {  it ->
            commentsViewModel.textToSpeech(it)
        })
        binding.recyclerView2.adapter=adapter

        commentsViewModel.read.observe(this, Observer{
            if(it!=null) {
                commentsViewModel.initial(commentsViewModel.textToSpeechEngine)
                val text = it.trim()
                commentsViewModel.speak(if (text.isNotEmpty()) text else "Text tidak boleh kosong")
                commentsViewModel.textToSpeechDone()
            }

        })

        commentsViewModel.expection.observe(this, Observer{
            if(it==true) {
                Toast.makeText(this,"Comment must be more than 50 words!",Toast.LENGTH_LONG).show()
              }

        })



        commentsViewModel.set.observe(this,Observer{
            Toast.makeText(this,"Comment is Added",Toast.LENGTH_LONG).show()

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


        commentsViewModel.initial(startForResult)
        floating_action_button.setOnClickListener {
            commentsViewModel.startRecording()
        }



    }
}


