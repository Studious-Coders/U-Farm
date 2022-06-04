package com.example.u_farm.home.solutions

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.chaquo.python.Python
import com.example.u_farm.R
import com.example.u_farm.databinding.ActivitySolutionsBinding
import com.example.u_farm.home.*
import com.example.u_farm.home.solutions.addsolutions.AddSolutionsActivity
import com.example.u_farm.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import java.util.*
var chosedlang="English"

class SolutionsActivity : AppCompatActivity() {
    private lateinit var solutionsViewModel: SolutionsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySolutionsBinding>(
            this,
            R.layout.activity_solutions
        )
        val args1: SolutionsActivityArgs by navArgs()

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#C4C4C4")))

        val application: Application = requireNotNull(this).application
        val viewModelFactory = SolutionsViewModelFactory(application, args1.problemUid.toString())
        solutionsViewModel =
            ViewModelProvider(this, viewModelFactory).get(SolutionsViewModel::class.java)
        binding.solutionsViewModel = solutionsViewModel

        binding.lifecycleOwner = this

        val adapter = SolutionsAdapter(SolutionsListener { statement->
            solutionsViewModel.textToSpeech(statement)
        }, IncreaseListener{ increase,suid ->
            Log.d("Clicking","Clicked")
            solutionsViewModel.increaseRating(increase,suid)

        },DecreaseListener {  decrease,suid ->
              solutionsViewModel.decreaseRating(decrease,suid)

        })

        //Initialize the adapter onClick event happen on each object (lamba function)
//        val adapter= SolutionsAdapter(ProblemsListener { username ->
//            solutionsViewModel.navigateToSolutionsPage(username)
//
//        })

        binding.recyclerView1.adapter = adapter

      solutionsViewModel.singleChanges.observe(this,Observer{
          if(it==true) {
              solutionsViewModel.intiate()
          }
      })

        solutionsViewModel.allData.observe(this, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })


        solutionsViewModel.navigateToAddSolutions.observe(this, Observer {
            if (it) {
                if (FirebaseAuth.getInstance().uid != null) {
                    if (it) {
                        val intent = Intent(this, AddSolutionsActivity::class.java)
                        intent.putExtra("problemUid", args1.problemUid.toString())

                        startActivity(intent)
                        solutionsViewModel.navigateToAddSolutionsDone()
                    }
                } else {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)

                }

            }
        })

        solutionsViewModel.read.observe(this,Observer {
            if (it !=null) {
                solutionsViewModel.initial(textToSpeechEngine)

                var text = it.trim()
                val py = Python.getInstance();
                val pyobj = py.getModule("translate")
                if(chosedlang =="Tamil")
                    text=pyobj.callAttr("tam",text).toString()
                else if(chosedlang =="English")
                    text=pyobj.callAttr("eng",text).toString()
                else
                    text=pyobj.callAttr("hin",text).toString()
                solutionsViewModel.speak(if (text.isNotEmpty()) text else "Text tidak boleh kosong")
                solutionsViewModel.textToSpeechDone()
            }
    })


    }



    private val textToSpeechEngine: TextToSpeech by lazy {
        TextToSpeech(this) {
            if (it == TextToSpeech.SUCCESS) {
                if(chosedlang =="Tamil")
                    textToSpeechEngine.language = Locale("ta-IN")
                else if(chosedlang =="English")
                    textToSpeechEngine.language = Locale("en-US")
                else
                    textToSpeechEngine.language = Locale("hi-IN")

            }
        }
    }
    companion object {
        val USER_KEY = "problem"
    }

}


//    val textToSpeechEngine: TextToSpeech by lazy {
//        TextToSpeech(this) {
//            if (it == TextToSpeech.SUCCESS) textToSpeechEngine.language = Locale("in_ID")
//        }
//    }

