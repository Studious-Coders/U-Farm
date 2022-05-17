package com.example.u_farm.profile.language

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.u_farm.R
import com.example.u_farm.databinding.ActivityLanguageBinding
import com.google.android.material.navigation.NavigationBarView
import kotlinx.android.synthetic.main.activity_language.*
import java.util.*

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
        supportActionBar?.hide()
        val adapter = ArrayAdapter.createFromResource(
            this,
            R.array.language,
            android.R.layout.simple_spinner_item
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                when(position){
                    0 -> spinner.setSelection(0)
                    1 -> spinner.setSelection(1)
                    2 -> spinner.setSelection(2)
                }
                val text = parent.getItemAtPosition(position).toString()
                languageViewModel.updateLanguage(text)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }


        }// to close the onItemSelected



        languageViewModel =
            ViewModelProvider(this, viewModelFactory).get(LanguageViewModel::class.java)

        binding.languageViewModel = languageViewModel
        binding.lifecycleOwner = this
    }


        override fun onNothingSelected(parent: AdapterView<*>) {

        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

        }







}