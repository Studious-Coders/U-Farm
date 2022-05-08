package com.example.u_farm.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.model.U_Farm
import com.google.firebase.auth.FirebaseUser
import org.kodein.di.android.subKodein

class LoginViewModel(application: Application): ViewModel() {

    private var authRepository: AuthRepository
    private val _navigateToRegister = MutableLiveData<Boolean>()
    val navigateToRegister: LiveData<Boolean>
        get() = _navigateToRegister

      fun skipAuthentication(){
          _skip.value=true
      }


    fun navigateToRegister() {
        _navigateToRegister.value = true
    }

    fun navigateToRegisterDone() {
        _navigateToRegister.value = false
    }

    val firebaseUser:LiveData<FirebaseUser?>
        get()=authRepository.getFirebaseUserMutableLiveData()

    private val _skip = MutableLiveData<Boolean>()
    val skip: LiveData<Boolean>
        get() = _skip

    init{
        authRepository= AuthRepository(application)
    }

    fun login(email:String,password:String){
        authRepository.login(email, password)

    }
}