package com.example.u_farm.login

import android.app.Activity
import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.model.U_Farm
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider

class LoginViewModel(application: Application,activity:Activity,private val listener: OnSignInStartedListener): ViewModel() {

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




    fun signInWithGoogle(idToken: String) {
       authRepository.firebaseAuthWithGoogle(idToken)
        }

//
//
//    fun firebaseAuthWithGoogle(idToken: String) {
//        val credential = GoogleAuthProvider.getCredential(idToken, null)
//        authRepository.auth.signInWithCredential(credential).addOnCompleteListener {
//            if (it.isSuccessful) {
//                authRepository.firebaseUserAuthRepository.postValue(authRepository.auth.currentUser)
//                val user = authRepository.auth.currentUser
//                val ufarm = U_Farm(
//                    user!!.uid,
//                    user.displayName.toString(),
//                    user.email.toString(),
//                    "",
//                    user.phoneNumber.toString(),
//                    user.photoUrl.toString()
//                )
//               authRepository.setUserData(ufarm)
//            }
//        }
//    }



}
interface OnSignInStartedListener {
    fun onSignInStarted(client: GoogleSignInClient?)
}