package com.example.u_farm.home.solutions

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import com.example.u_farm.R
import com.example.u_farm.databinding.ActivityAddProblemsBinding
import com.example.u_farm.databinding.ActivitySolutionsBinding
import com.example.u_farm.home.*
import com.example.u_farm.home.solutions.addsolutions.AddSolutionsActivity
import com.example.u_farm.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.list_items2.*
import java.util.*

class SolutionsActivity : AppCompatActivity() {
    private lateinit var solutionsViewModel: SolutionsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySolutionsBinding>(
            this,
            R.layout.activity_solutions
        )
        val args1:SolutionsActivityArgs by navArgs()

        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#C4C4C4")))

        val application: Application = requireNotNull(this).application
        val activity: Activity =this
        val viewModelFactory = SolutionsViewModelFactory(application,activity)
        solutionsViewModel =
            ViewModelProvider(this, viewModelFactory).get(SolutionsViewModel::class.java)
        binding.solutionsViewModel=solutionsViewModel

        binding.lifecycleOwner = this

        val adapter=SolutionsAdapter()
        //Initialize the adapter onClick event happen on each object (lamba function)
//        val adapter= SolutionsAdapter(ProblemsListener { username ->
//            solutionsViewModel.navigateToSolutionsPage(username)
//
//        })

        binding.recyclerView1.adapter=adapter


        solutionsViewModel.allData.observe(this, Observer {
            it?.let{
                adapter.submitList(it)
            }
        })

        solutionsViewModel.argument.observe(this, Observer {
            if(it==true){
                solutionsViewModel.getSolutionOfTheGetProblem(args1)
            }
        })


        solutionsViewModel.navigateToAddSolutions.observe(this, Observer {
           if(it) {
               if(FirebaseAuth.getInstance().uid!=null){
                   if(it){
                       val intent = Intent(this, AddSolutionsActivity::class.java)
                       intent.putExtra(USER_KEY,args1.toString())


                       startActivity(intent)
                       solutionsViewModel.navigateToAddSolutionsDone()
                   }
               }else{
                   val intent = Intent(this, LoginActivity::class.java)
                   startActivity(intent)

               }

            }
       })
    }

    companion object{
        val USER_KEY="problem"
    }

//        solutionsViewModel.initial(textToSpeechEngine)
//        floating_action_button6.setOnClickListener {
//            val text = solutionstatement.text?.trim().toString()
//            solutionsViewModel.speak(if (text.isNotEmpty()) text else "Text tidak boleh kosong")
//        }

    }

//    val textToSpeechEngine: TextToSpeech by lazy {
//        TextToSpeech(this) {
//            if (it == TextToSpeech.SUCCESS) textToSpeechEngine.language = Locale("in_ID")
//        }
//    }

