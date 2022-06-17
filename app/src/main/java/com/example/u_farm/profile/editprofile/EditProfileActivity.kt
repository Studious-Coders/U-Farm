package com.example.u_farm.profile.editprofile

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.u_farm.R
import com.example.u_farm.databinding.ActivityEditprofileBinding
import com.example.u_farm.model.U_Farm
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_editprofile.*

class EditProfileActivity : AppCompatActivity() {
    private var u_farm = U_Farm()
    private lateinit var editProfileViewModel:EditProfileViewModel
    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityEditprofileBinding>(
            this,
            R.layout.activity_editprofile
        )

        jobText.showSoftInputOnFocus=false
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#C4C4C4")))
        supportActionBar?.setTitle(R.string.edit_profile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val application: Application = requireNotNull(this).application
        val activity: Activity =this
        val viewModelFactory = EditProfileViewModelFactory(application,activity)
        editProfileViewModel =
            ViewModelProvider(this, viewModelFactory).get(EditProfileViewModel::class.java)
        binding.uFarm=u_farm
        binding.editProfileViewModel = editProfileViewModel
        binding.lifecycleOwner = this

        editProfileViewModel.image.observe(this, Observer {
            if(it==true) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startActivityForResult(intent, 0)
            }
        })


        editProfileViewModel.upload.observe(this,Observer{
            if(it==true) {
                editProfileViewModel.upload1()

            }

        })

        editProfileViewModel.setData.observe(this,Observer{
            if(it==true){
                loading_spinner.visibility=View.GONE
                Toast.makeText(this, "Your profile is updated successfully", Toast.LENGTH_LONG)
                    .show()
                editProfileViewModel.function()
            }
        })
        editProfileViewModel.spinner.observe(this,Observer{
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
            editProfileViewModel.imageFormatingDone(selectedPhotoUri!!)
            Glide.with(this).load(selectedPhotoUri).into(userdp1)
            Picasso.get().load(selectedPhotoUri).into(userdp1)
        }
    }


}