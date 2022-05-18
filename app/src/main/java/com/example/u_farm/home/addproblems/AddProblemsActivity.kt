package com.example.u_farm.home.addproblems

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.u_farm.R
import com.example.u_farm.databinding.ActivityAddProblemsBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_problems.*
import kotlinx.android.synthetic.main.activity_editprofile.*

class AddProblemsActivity : AppCompatActivity() {
    private lateinit var addProblemsViewModel:AddProblemsViewModel
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityAddProblemsBinding>(
            this,
            R.layout.activity_add_problems
        )
        supportActionBar?.title="Add Problems"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val application: Application = requireNotNull(this).application
        val activity: Activity =this
        val viewModelFactory = AddProblemsViewModelFactory(application,activity)
        addProblemsViewModel =
            ViewModelProvider(this, viewModelFactory).get(AddProblemsViewModel::class.java)
        binding.addProblemsViewModel=addProblemsViewModel

        binding.lifecycleOwner = this

        addProblemsViewModel.edit.observe(this, Observer {
            if(it==true){
             addProblemsViewModel.str= convertText1.text.toString()
                addProblemsViewModel.editDone()
            }
        })

        floating_action_button.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            when (motionEvent.action){
                MotionEvent.ACTION_DOWN -> {
                  addProblemsViewModel.stopRecording()
                }
                MotionEvent.ACTION_UP -> {
                    val folder = "${externalCacheDir?.absolutePath}/audiorecordtest.3gp"
                    addProblemsViewModel.startRecording(folder)
                }
            }
            return@OnTouchListener false
        })
        addProblemsViewModel.setData.observe(this, Observer {
            if(it==true){
                loading_spinner.visibility= View.GONE
                Toast.makeText(this,"Your Problem is Added", Toast.LENGTH_LONG).show()
                    addProblemsViewModel.function()
                finish()
            }
        })




        addProblemsViewModel.image.observe(this, Observer {
            if(it==true) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, 0)

            }
        })
            addProblemsViewModel.setImage.observe(this, Observer {
            if(it!=null){
                loading_spinner.visibility= View.GONE
            }
        })

        addProblemsViewModel.spinner.observe(this, Observer {
            if(it==true){
                loading_spinner.visibility= View.VISIBLE
            }
        })

    }

    var selectedPhotoUri: Uri? = null
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            Log.d("EditProfile", "Photo was selected")
            selectedPhotoUri = data.data
            addProblemsViewModel.imageFormatingDone(selectedPhotoUri!!)
            Picasso.with(this).load(selectedPhotoUri).into(imageView)
            loading_spinner.visibility= View.VISIBLE


        }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean{
        when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onContextItemSelected(item)
    }







}
