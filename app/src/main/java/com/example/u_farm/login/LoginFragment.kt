package com.example.u_farm.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.u_farm.HomeActivity
import com.example.u_farm.R
import com.example.u_farm.databinding.FragmentLoginBinding
import com.example.u_farm.model.U_Farm

class LoginFragment : Fragment() {
    private var u_farm:U_Farm= U_Farm()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentLoginBinding =DataBindingUtil.inflate(inflater,
            R.layout.fragment_login,container,false)
        (activity as AppCompatActivity).supportActionBar?.hide()
        val application= requireNotNull(this.activity).application
        val viewModelFactory=LoginViewModelFactory(application)
        val loginViewModel = ViewModelProvider(this,viewModelFactory).get(LoginViewModel::class.java)
        binding.loginViewModel=loginViewModel
        binding.uFarm=u_farm
        binding.lifecycleOwner=this
        loginViewModel.firebaseUser.observe(viewLifecycleOwner, Observer{
            if(it!=null){
                Toast.makeText(application,"Welcome back!", Toast.LENGTH_LONG).show()
            val intent= Intent(application, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        })

//        loginViewModel.skip.observe(viewLifecycleOwner, Observer{
//            if(it == true){
//                Toast.makeText(application,"Welcome to U-Farm App", Toast.LENGTH_LONG).show()
//                val intent= Intent(application, HomeActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
//                startActivity(intent)
//
//            }
//        })
        loginViewModel.navigateToRegister.observe(viewLifecycleOwner, Observer {
            if(it) {
                this.findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
                loginViewModel.navigateToRegisterDone()
            }
        })

        return binding.root
    }

}



