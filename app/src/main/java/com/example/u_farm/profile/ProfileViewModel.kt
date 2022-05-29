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

    var chosedlang=1

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

    private val _expert= MutableLiveData<Boolean?>()
    val expert: LiveData<Boolean?>
        get()=_expert


    val value= MediatorLiveData<U_Farm>()

    val getData: LiveData<U_Farm?>
        get()=authRepository.getUserDataMutableLiveData()

    val setData: LiveData<Boolean?>
        get()=authRepository.setUserDataMutableLiveData()


    private val _snackbar= MutableLiveData<Boolean?>()
    val snackbar: LiveData<Boolean?>
        get()=_snackbar




    init {
        authRepository = AuthRepository(application)
        alert = AlertDialog.Builder(activity)
        alert0 = AlertDialog.Builder(activity)
        authRepository.getUserData()
        value.addSource(getData,value::setValue)
        if(getData.value?.job=="Expert"){
            _expert.value=true
        }


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
//        if(getData.value?.uid!=null) {

            alert.show()
//        }else{
//            _snackbar.value=true
//        }

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

    fun shareIntentDone() {
        _share.value = false
    }
    fun snackbarDone(){
        _snackbar.value=false
    }

    fun snackbar(){
        _snackbar.value=true
    }
    val  options = arrayOf("Tamil", "English","Hindi")
    fun updateLanguage(lang:Int){
        chosedlang=lang
        authRepository.singleRecord(options[lang],"language")
    }
    fun languageIntent(){

        alert0.setTitle("Choose a language")

        alert0.setItems(options) { dialog, which ->
            dialog.dismiss()

            when (which) {
                /* execute here your actions */
                0 ->   updateLanguage(0)
                1 ->   updateLanguage(1)
                2 ->   updateLanguage(2)
            }


        }
//        if(getData.value?.uid!=null) {

            alert0.show()
//        }else{
//            _snackbar.value=true
//        }
        _language.value=true
    }

    fun languageIntentDone(){
        _language.value=false
    }







}