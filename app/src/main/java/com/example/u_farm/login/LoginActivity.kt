package com.example.u_farm.login

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.u_farm.R

import com.example.u_farm.databinding.ActivityLoginBinding
import com.example.u_farm.model.U_Farm
import com.example.u_farm.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private var u_farm: U_Farm = U_Farm()
    private lateinit var loginViewModel: LoginViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityLoginBinding>(
            this,
            R.layout.activity_login
        )
        supportActionBar?.hide()



        val application: Application = requireNotNull(this).application
        val activity: Activity =this
        val viewModelFactory = LoginViewModelFactory(application,activity)
        loginViewModel =
            ViewModelProvider(this, viewModelFactory).get(LoginViewModel::class.java)
        binding.loginViewModel=loginViewModel
        binding.uFarm=u_farm

        binding.lifecycleOwner = this
        loginViewModel.firebaseUser.observe(this, Observer{
            if(it!=null){
                (activity as AppCompatActivity).finish()
                Toast.makeText(application,"Welcome back!", Toast.LENGTH_LONG).show()
            }
        })

        loginViewModel.navigateToRegister.observe(this, Observer {
            if(it) {
                             val intent= Intent(this, RegisterActivity::class.java)

                startActivity(intent)
                  finish()
                              loginViewModel.navigateToRegisterDone()
            }
        })






    }
}