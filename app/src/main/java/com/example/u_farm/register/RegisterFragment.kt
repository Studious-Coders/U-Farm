package com.example.u_farm.register

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
import com.example.u_farm.databinding.FragmentRegisterBinding
import com.example.u_farm.model.U_Farm

class RegisterFragment : Fragment() {
    private var u_farm:U_Farm = U_Farm()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentRegisterBinding =DataBindingUtil.inflate(inflater,
            R.layout.fragment_register,container,false)
        (activity as AppCompatActivity).supportActionBar?.hide()
        val application= requireNotNull(this.activity).application
        val viewModelFactory=RegisterViewModelFactory(application)
        val registerViewModel=ViewModelProvider(this,viewModelFactory).get(RegisterViewModel::class.java)
       binding.uFarm=u_farm
        binding.registerViewModel=registerViewModel
        binding.lifecycleOwner=this


        registerViewModel.firebaseUser.observe(viewLifecycleOwner, Observer {
            if(it!=null){
                Toast.makeText(application,"Welcome ${u_farm.username}!", Toast.LENGTH_LONG).show()
                val intent= Intent(application, HomeActivity::class.java)
                intent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
        })

        registerViewModel.navigateToLogin.observe(viewLifecycleOwner, Observer {
            if(it){
                this.findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                registerViewModel.navigateToLoginDone()
            }
        })

        return binding.root
    }

}