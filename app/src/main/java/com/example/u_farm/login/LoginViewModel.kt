package com.example.u_farm.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.u_farm.database.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class LoginViewModel(application: Application): ViewModel() {

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
        viewModelScope.launch {

        }
        authRepository.login(email, password)

    }

    fun signInWithGoogle(idToken: String) {
        authRepository.firebaseAuthWithGoogle(idToken)
    }

}
