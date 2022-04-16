package com.example.u_farm

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.u_farm.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding=DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
        val navController=this.findNavController(R.id.myNavHostFragment)
        appBarConfiguration= AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController,appBarConfiguration)
    }
}