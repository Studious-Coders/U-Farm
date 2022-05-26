package com.example.u_farm.home.addproblems

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.u_farm.R
import com.example.u_farm.databinding.ActivityAddProblemsBinding
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_add_problems.*
import kotlinx.android.synthetic.main.activity_editprofile.*
import java.util.*

private const val REQUEST_RECORD_AUDIO_PERMISSION = 200
class AddProblemsActivity : AppCompatActivity() {

    private lateinit var addProblemsViewModel:AddProblemsViewModel
    private lateinit var progressBar: ProgressDialog
    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)
   // private val model: AddProblemsViewModel by viewModels()
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
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






       // ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#C4C4C4")))


        progressBar=ProgressDialog(this)
        supportActionBar?.title="Add Problems"

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val application: Application = requireNotNull(this).application
        val activity: Activity =this
        val viewModelFactory = AddProblemsViewModelFactory(application,activity)
        addProblemsViewModel =
            ViewModelProvider(this, viewModelFactory).get(AddProblemsViewModel::class.java)
        binding.addProblemsViewModel=addProblemsViewModel

        binding.lifecycleOwner = this

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
        val startForResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val spokenText: String? =
                    result.data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                        .let { text -> text?.get(0) }
                binding.convertText1.setText(spokenText)
                addProblemsViewModel.editDone()
            }
        }

        addProblemsViewModel.initial(startForResult)
//        with(binding) {
            floating_action_button.setOnClickListener { addProblemsViewModel.startRecording() }
//            fabPlay.setOnClickListener {
//                val text = edtText.text?.trim().toString()
//                model.speak(if (text.isNotEmpty()) text else "Text tidak boleh kosong")
//            }
//        }

        floating_action_button.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            when (motionEvent.action){
                MotionEvent.ACTION_DOWN -> {
                  addProblemsViewModel.stopRecording()


//        val textToSpeechEngine: TextToSpeech by lazy {
//            TextToSpeech(this) {
//                if (it == TextToSpeech.SUCCESS) textToSpeechEngine.language = Locale("in_ID")
//            }
//        }


        addProblemsViewModel.spinner.observe(this,Observer{
            if(it!=null) {
                progressBar.setMessage("Uploading the Problem.....")
                progressBar.show()
                addProblemsViewModel.upload()

            }
        })

        addProblemsViewModel.setImage.observe(this,Observer{
            if(it!=null) {
                addProblemsViewModel.upload1()

            }
        })

        addProblemsViewModel.setData.observe(this, Observer {
            if(it==true){

                loading_spinner2.visibility= View.GONE
                Toast.makeText(this,"Your Problem is Added", Toast.LENGTH_LONG).show()
                progressBar.dismiss()
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
                loading_spinner2.visibility= View.GONE
                progressBar.dismiss()

            }
        })


        addProblemsViewModel.spinner.observe(this, Observer {
            if(it==true){
                loading_spinner2.visibility= View.VISIBLE
            }
        })



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
    addProblemsViewModel.setData.observe(this, Observer {
        if(it==true){
     progressBar.dismiss()
        }
    })
}

    var selectedPhotoUri: Uri? = null
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            Log.d("EditProfile", "Photo was selected")
            textView.visibility=View.GONE
            imageView2.visibility=View.GONE

            selectedPhotoUri = data.data
            addProblemsViewModel.imageFormatingDone(selectedPhotoUri!!)
            Picasso.with(this).load(selectedPhotoUri).into(imageView)
            loading_spinner2.visibility= View.VISIBLE
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
