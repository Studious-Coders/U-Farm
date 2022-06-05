package com.example.u_farm.home.addproblems

import android.Manifest
import android.annotation.SuppressLint
import android.app.Application
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.u_farm.R
import com.example.u_farm.databinding.ActivityAddProblemsBinding
import com.example.u_farm.model.Problem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_problems.*
import java.lang.StringBuilder

private const val REQUEST_RECORD_AUDIO_PERMISSION = 200

class AddProblemsActivity : AppCompatActivity() {
    private var problem: Problem =Problem()
    private lateinit var addProblemsViewModel:AddProblemsViewModel
    private lateinit var progressBar: ProgressDialog
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
        if (!permissionToRecordAccepted) finish()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityAddProblemsBinding>(
            this,
            R.layout.activity_add_problems
        )

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#C4C4C4")))
        progressBar=ProgressDialog(this)
        supportActionBar?.setTitle(R.string.add_problems)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val application: Application = requireNotNull(this).application
        val viewModelFactory = AddProblemsViewModelFactory(application)
        addProblemsViewModel = ViewModelProvider(this, viewModelFactory).get(AddProblemsViewModel::class.java)
        binding.addProblemsViewModel=addProblemsViewModel
        binding.problem=problem
        binding.lifecycleOwner = this

        //Speech ToText
        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult())
        { result ->
            if (result.resultCode == RESULT_OK) {
                val spokenText: String? =
                    result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                        .let { text -> text?.get(0)
                        }
                binding.convertText1.setText(spokenText)
            }
        }

        addProblemsViewModel.initial(startForResult)
            floating_action_button.setOnClickListener {
                addProblemsViewModel.startRecording()
            }



        addProblemsViewModel.uploading.observe(this,Observer{
            if(it!=null) {
                progressBar.setMessage("Uploading the Prblem.....")
                progressBar.show()

            }
        })

        addProblemsViewModel.setProblemData.observe(this,Observer{
            if(it!=null) {
                Toast.makeText(this,"Your Problem is Added", Toast.LENGTH_LONG).show()
                addProblemsViewModel.uploaded()
                progressBar.dismiss()

            }
        })

        addProblemsViewModel.expection.observe(this,Observer{
            if(it!=null) {
                Toast.makeText(this,"You have to both upload a picture and give some description about it.", Toast.LENGTH_LONG).show()
            }
        })


        addProblemsViewModel.image.observe(this, Observer {
            if(it==true) {
                progressBar.setMessage("Uploaing Picture")
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, 0)
            }
        })

        addProblemsViewModel.setImage.observe(this, Observer {
            if(it!=null) {
                progressBar.dismiss()
            }
        })

    }


    var selectedPhotoUri: Uri? = null
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            progressBar.show()
            Log.d("Add Problems", "Photo was selected")
            textView.visibility=View.GONE
            imageView2.visibility=View.GONE

            selectedPhotoUri = data.data
            addProblemsViewModel.imageFormatingDone(selectedPhotoUri!!)
            Picasso.with(this).load(selectedPhotoUri).into(imageView)
          }
    }


}


