package com.example.u_farm.register

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
import com.example.u_farm.databinding.ActivityRegisterBinding
import com.example.u_farm.login.LoginActivity
import com.example.u_farm.model.U_Farm
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private var u_farm: U_Farm = U_Farm()
    private lateinit var registerViewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityRegisterBinding>(
            this,
            R.layout.activity_register
        )
        supportActionBar?.hide()

        val application: Application = requireNotNull(this).application
        val activity: Activity =this
        val viewModelFactory = RegisterViewModelFactory(application)
        registerViewModel =
            ViewModelProvider(this, viewModelFactory).get(RegisterViewModel::class.java)
        binding.registerViewModel=registerViewModel
        binding.uFarm=u_farm

        binding.lifecycleOwner = this
        registerViewModel.firebaseUser.observe(this, Observer {
            if(it!=null){
                Toast.makeText(application,"Welcome ${u_farm.username}!", Toast.LENGTH_LONG).show()
                (activity as AppCompatActivity).finish()
            }
        })

        registerViewModel.navigateToLogin.observe(this, Observer {
            if(it){
                val intent= Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                registerViewModel.navigateToLoginDone()
            }
        })



    }
}