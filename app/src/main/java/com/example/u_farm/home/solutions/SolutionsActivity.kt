package com.example.u_farm.home.solutions

import android.annotation.SuppressLint
import android.app.Application
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.navArgs
import com.chaquo.python.Python
import com.example.u_farm.R
import com.example.u_farm.databinding.ActivitySolutionsBinding
import com.example.u_farm.home.*
import com.example.u_farm.home.solutions.addsolutions.AddSolutionsActivity
import com.example.u_farm.home.solutions.comment.CommentsActivity
import com.example.u_farm.login.LoginActivity
import com.example.u_farm.util.lang
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_solutions.*

class SolutionsActivity : AppCompatActivity() {
    companion object{
        val USER_KEY="key"
    }

    private lateinit var solutionsViewModel: SolutionsViewModel

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivitySolutionsBinding>(
            this,
            R.layout.activity_solutions
        )
        Log.d("onCreate","Created")

        val args1: SolutionsActivityArgs by navArgs()
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#C4C4C4")))

        val application: Application = requireNotNull(this).application
        val viewModelFactory = SolutionsViewModelFactory(application, args1.problemUid.toString())
        solutionsViewModel = ViewModelProvider(this, viewModelFactory).get(SolutionsViewModel::class.java)
        binding.solutionsViewModel = solutionsViewModel
        binding.lifecycleOwner = this


        val adapter = SolutionsAdapter(SolutionsListener1 { it ->
            val intent = Intent(this, CommentsActivity::class.java)
            intent.putExtra(USER_KEY, it)
            Log.d("solutionUid",it)
            startActivity(intent)


        },SolutionsListener { statement->
            solutionsViewModel.textToSpeech(statement)
        }, IncreaseListener{ increase,suid ->
            Log.d("Clicking","Clicked")
            solutionsViewModel.increaseRating(increase,suid)
        },DecreaseListener {  decrease,suid ->
            solutionsViewModel.decreaseRating(decrease,suid)

        })

        binding.recyclerView1.adapter = adapter


        solutionsViewModel.allData.observe(this, Observer {
            it?.let {
                loading_spinner3.visibility= View.GONE
                adapter.submitList(it)
                adapter.notifyDataSetChanged()
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
                solutionsViewModel.initial(solutionsViewModel.textToSpeechEngine)
                var text = it.trim()

                val py = Python.getInstance()
                val pyobj = py.getModule("translate")
                text=pyobj.callAttr(lang,text).toString()
                solutionsViewModel.speak(if (text.isNotEmpty()) text else "Text tidak boleh kosong")
                solutionsViewModel.textToSpeechDone()

            }

        })



    }





}

