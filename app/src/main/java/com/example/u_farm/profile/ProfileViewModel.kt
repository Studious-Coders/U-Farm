package com.example.u_farm.profile

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.DialogInterface
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.model.U_Farm
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class ProfileViewModel(application: Application, activity: Activity): ViewModel() {
    private var alert: AlertDialog.Builder
    private var alert0: AlertDialog.Builder

    private var _navigateToEditProfile= MutableLiveData<Boolean>()
    val navigateToEditProfile: LiveData<Boolean>
        get()=_navigateToEditProfile

    private var authRepository: AuthRepository
    val loggedUser: LiveData<Boolean?>
        get()=authRepository.getUserLoggedMutableLiveData()

    private val _share= MutableLiveData<Boolean?>()
    val share: LiveData<Boolean?>
        get()=_share

    private val _language= MutableLiveData<Boolean?>()
    val language: LiveData<Boolean?>
        get()=_language

    val value= MediatorLiveData<U_Farm>()

    val getData: LiveData<U_Farm?>
        get()=authRepository.getUserDataMutableLiveData()



    init {
        authRepository = AuthRepository(application)
        alert = AlertDialog.Builder(activity)
        alert0 = AlertDialog.Builder(activity)
        authRepository.getUserData()
        value.addSource(getData,value::setValue)


    }

    fun function(){
        authRepository.getUserData()
    }

    fun logOutAlertDialogBox(){

        alert.setMessage("Are you sure you want to Signout?")
            .setPositiveButton("YES", DialogInterface.OnClickListener { dialog, which ->
                authRepository.signOut()
            }).setNegativeButton("NO", null)

        val alert1: AlertDialog = alert.create()
        alert1.show()
    }
    fun navigateToEditProfile(){
        _navigateToEditProfile.value=true
    }

    fun navigateToEditProfileDone(){
        _navigateToEditProfile.value=false
    }

    fun shareIntent(){
        _share.value=true
    }

    fun shareIntentDone(){
        _share.value=false

    }

    fun updateLanguage(lang:String){
        authRepository.singleRecord(lang,"language")
    }
    fun languageIntent(){

        alert0.setTitle("Choose a language")

        val options = arrayOf("Tamil", "English","Hindi")

        alert0.setItems(options) { dialog, which ->
            dialog.dismiss()

            when (which) {
                /* execute here your actions */
                0 ->   updateLanguage(options[0])
                1 ->   updateLanguage(options[1])
                2 ->updateLanguage(options[2])
            }


        }


        alert0.show()
        _language.value=true
    }

    fun languageIntentDone(){
        _language.value=false
    }







}