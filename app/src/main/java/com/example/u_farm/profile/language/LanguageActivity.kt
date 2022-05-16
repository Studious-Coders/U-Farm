package com.example.u_farm.profile.language

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.u_farm.R
import com.example.u_farm.databinding.ActivityEditprofileBinding
import com.example.u_farm.databinding.ActivityLanguageBinding
import com.example.u_farm.model.U_Farm
import com.example.u_farm.profile.editprofile.EditProfileViewModel
import com.example.u_farm.profile.editprofile.EditProfileViewModelFactory
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationBarView.OnItemSelectedListener
import kotlinx.android.synthetic.main.activity_language.*
import java.util.*
import kotlin.collections.ArrayList

class LanguageActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {
    private lateinit var languageViewModel: LanguageViewModel

    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityLanguageBinding>(
            this,
            R.layout.activity_language
        )
        val application: Application = requireNotNull(this).application
        val activity: Activity = this
        val viewModelFactory = LanguageViewModelFactory(application, activity)

        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.language,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = this
        languageViewModel =
            ViewModelProvider(this, viewModelFactory).get(LanguageViewModel::class.java)

        binding.languageViewModel = languageViewModel
        binding.lifecycleOwner = this
    }


        override fun onNothingSelected(parent: AdapterView<*>) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val text = parent?.getItemAtPosition(position).toString()
            languageViewModel.updateLanguage(text)
        }







}