package com.example.u_farm

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class HomeActivity : AppCompatActivity() {
    lateinit var bottomNavigationView:BottomNavigationView

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding=DataBindingUtil.setContentView<ActivityHomeBinding>(this,R.layout.activity_home)

        bottomNavigationView = binding.bottomNavigation

         supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#C4C4C4")))
        val navController=findNavController(R.id.fragmentContainerView)
        val appBarConfiguration= AppBarConfiguration(setOf(R.id.home,R.id.news,R.id.profile))

        setupActionBarWithNavController(navController,appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)

        val cm: ConnectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if(cm.activeNetwork== null && cm.isDefaultNetworkActive){
            Snackbar.make(binding.root,"No Internet Connection", Snackbar.LENGTH_LONG).show()
        }else{
            Snackbar.make(binding.root,"Internet Connected", Snackbar.LENGTH_LONG).show()

        }

        if (!Python.isStarted()) {
            Python.start(AndroidPlatform(this))
        }

        getUserData()

    }

}