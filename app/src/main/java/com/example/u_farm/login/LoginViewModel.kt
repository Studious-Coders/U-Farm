package com.example.u_farm.login

import android.app.Activity
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.u_farm.database.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel(private val application: Application,private val listener: OnSignInStartedListener): ViewModel() {

    private var authRepository: AuthRepository
    private val _navigateToRegister = MutableLiveData<Boolean>()
    val navigateToRegister: LiveData<Boolean>
        get() = _navigateToRegister



    fun navigateToRegister() {
        _navigateToRegister.value = true
    }

    fun navigateToRegisterDone() {
        _navigateToRegister.value = false
    }
    val firebaseUser:LiveData<FirebaseUser?>
        get()=authRepository.getFirebaseUserMutableLiveData()
    init{
        authRepository= AuthRepository(application)

    }

    fun login(email:String,password:String){
        authRepository.login(email, password)

    }
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken("492960336873-kpmq8gmn37riibaoasms8h9ld5s8r6qo.apps.googleusercontent.com")
        .requestEmail()
        .build()

    private val googleSignInClient = GoogleSignIn.getClient(application, gso)




    fun signIn() {
        listener.onSignInStarted(googleSignInClient)
    }

     fun gsign(idToken:String){
         authRepository.firebaseAuthWithGoogle(idToken)
     }




}
interface OnSignInStartedListener {
    fun onSignInStarted(client: GoogleSignInClient?)
}