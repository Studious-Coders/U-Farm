package com.example.u_farm.home.solutions.comment

import android.annotation.SuppressLint
import android.app.Application
import android.app.ProgressDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.chaquo.python.Python
import com.example.u_farm.R
import com.example.u_farm.databinding.ActivityCommentsBinding
import com.example.u_farm.home.CommentsAdapter
import com.example.u_farm.home.CommentsListener
import com.example.u_farm.home.solutions.SolutionsActivity
import com.example.u_farm.model.Comments
import com.example.u_farm.util.lang

class CommentsActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressDialog
    private lateinit var problem: String
    private lateinit var commentsViewModel: CommentsViewModel
    private var comments:Comments= Comments()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityCommentsBinding>(
            this,
            R.layout.activity_comments
        )
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#C4C4C4")))
        progressBar = ProgressDialog(this)
       supportActionBar?.setTitle(R.string.comments)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        problem=intent.getStringExtra(SolutionsActivity.USER_KEY).toString()

       Log.d("IntentData",problem)
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

        commentsViewModel.allData.observe(this,Observer{
            it?.let{
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
            }

        })



        commentsViewModel.read.observe(this, Observer{
            if(it!=null) {
                commentsViewModel.initial(commentsViewModel.textToSpeechEngine)
                var text = it.trim()
                val py = Python.getInstance();
                val pyobj = py.getModule("translate")
                text=pyobj.callAttr(lang,text).toString()
                commentsViewModel.speak(if (text.isNotEmpty()) text else "Text tidak boleh kosong")
                commentsViewModel.textToSpeechDone()
            }

        })

        commentsViewModel.expection.observe(this, Observer{
            if(it==true) {
                Toast.makeText(this,"Comment must contain atleast 10 words!",Toast.LENGTH_LONG).show()
              }

        })



        commentsViewModel.uploaded.observe(this,Observer{
            if(it==true) {
                Toast.makeText(this, "Comment is Added", Toast.LENGTH_LONG).show()
                commentsViewModel.uploaded()

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


        commentsViewModel.initialing(startForResult)



    }




}


