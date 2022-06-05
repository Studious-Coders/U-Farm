package com.example.u_farm.register

import android.app.Activity
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.u_farm.database.AuthRepository
import com.google.firebase.auth.FirebaseUser

class RegisterViewModel(application: Application): ViewModel() {

    private var authRepository: AuthRepository
    private val _navigateToLogin= MutableLiveData<Boolean>()
    val navigateToLogin: LiveData<Boolean>
        get()=_navigateToLogin

    fun navigateToLogin(){
        _navigateToLogin.value=true
    }

    fun navigateToLoginDone(){
        _navigateToLogin.value=false
    }
    val firebaseUser: LiveData<FirebaseUser?>
        get()= authRepository.getFirebaseUserMutableLiveData()


    init{
        authRepository= AuthRepository(application)
    }

    fun register(username:String,email:String,password:String){
        authRepository.register(username,email, password)


    }
}