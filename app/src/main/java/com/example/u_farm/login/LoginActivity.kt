package com.example.u_farm.login

import android.app.Activity
import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    companion object {
        private const val TAG="signIn"
        private const val RC_SIGN_IN = 78
    }
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

        val viewModelFactory = LoginViewModelFactory(application,activity, object : OnSignInStartedListener {
            override fun onSignInStarted(client: GoogleSignInClient?) {
                startActivityForResult(client?.signInIntent, RC_SIGN_IN)
            }
        })
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

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("492960336873-n43hme2dfhsrml7ofcp0fc51ljinp944.apps.googleusercontent.com")
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this, gso)

        signInButton.setOnClickListener{
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)

//            override fun onSignInStarted(client: GoogleSignInClient?) {
//                startActivityForResult(client?.signInIntent, RC_SIGN_IN)
//            }

        }


    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.w(TAG, "Signup" + requestCode)

        if (requestCode == RC_SIGN_IN) {

            // this task is responsible for getting ACCOUNT SELECTED
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                loginViewModel.signInWithGoogle(account.idToken!!)
//                   loginViewModel.firebaseAuthWithGoogle(account.idToken!!)

                Toast.makeText(this, "Signed In Successfully", Toast.LENGTH_SHORT).show()

            } catch (e: ApiException) {
                Toast.makeText(this, e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }
}

