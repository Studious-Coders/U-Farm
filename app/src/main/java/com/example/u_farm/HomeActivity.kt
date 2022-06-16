package com.example.u_farm

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.chaquo.python.Python
import com.chaquo.python.android.AndroidPlatform
import com.example.u_farm.databinding.ActivityHomeBinding
import com.example.u_farm.util.getUserData
import com.example.u_farm.util.initialzePython
import com.google.android.material.bottomnavigation.BottomNavigationView


class HomeActivity : AppCompatActivity() {
    lateinit var bottomNavigationView:BottomNavigationView

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding=DataBindingUtil.setContentView<ActivityHomeBinding>(this,R.layout.activity_home)

        bottomNavigationView = binding.bottomNavigation

         supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#C4C4C4")))
        val navController=findNavController(R.id.fragmentContainerView)
        val appBarConfiguration= AppBarConfiguration(setOf(R.id.home,R.id.profile))

        setupActionBarWithNavController(navController,appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)



        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }

        getUserData()

        initialzePython()




    }



}