package com.example.u_farm.profile

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.DialogInterface
import android.content.res.Resources
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.chaquo.python.Python
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.model.U_Farm
import com.example.u_farm.util.languageInitial
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import java.util.*

@Suppress("DEPRECATION")
var chosedlang="English"
class ProfileViewModel(application: Application, activity: Activity): ViewModel() {
    private var alert: AlertDialog.Builder
    private var alert0: AlertDialog.Builder
    lateinit var locale: Locale




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

    private val _arguments= MutableLiveData<Int?>()
    val arguments: LiveData<Int?>
        get()=_arguments



    val value= MediatorLiveData<U_Farm>()

    val getData: LiveData<U_Farm?>
        get()=authRepository.getUserDataMutableLiveData()

    val setData: LiveData<Boolean?>
        get()=authRepository.setUserDataMutableLiveData()


    private val _snackbar= MutableLiveData<Boolean?>()
    val snackbar: LiveData<Boolean?>
        get()=_snackbar


    var currentLanguage:String=""

    init {
        authRepository = AuthRepository(application)
        alert = AlertDialog.Builder(activity)
        alert0 = AlertDialog.Builder(activity)
        authRepository.getUserData()
        value.addSource(getData,value::setValue)
            _expert.value=true

    }

    private var _choselang= MutableLiveData<String>()
    val choselang: LiveData<String>
        get()=_choselang

    fun languageset():String{
        _choselang.value=getData.value!!.language
        val option=_choselang.value.toString()
        return option
    }

    fun expertTickDone(){
        _expert.value=false
    }
    fun function(){
        authRepository.getUserData()
        _expert.value=true
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
    fun updateLanguage(lang:String){
        //_arguments.value=lang
        authRepository.singleRecord(lang,"language")


    }
    fun languageIntent() {

        alert0.setTitle("Choose a language")

        alert0.setItems(options) { dialog, which ->
            dialog.dismiss()

            when (which) {
                /* execute here your actions */
                0 -> {
                    setLocale("ta")
                    updateLanguage("ta_IN")
                    languageInitial("ta_IN")

                   }
                1 -> {
                    setLocale("en")
                    updateLanguage("en_US")
                    languageInitial("en_US")
                  }
                2 -> {
                    setLocale("hi")
                    updateLanguage("hi_IN")
                    languageInitial("hi_IN")
                  }

            }


        }
//        if(getData.value?.uid!=null) {

            alert0.show()
//        }else{
//            _snackbar.value=true
//        }
        _language.value=true
    }
    var reso:Resources=application.resources
    fun setLocale(localeName: String) {
            locale = Locale(localeName)
            val res = reso
            val dm = res.displayMetrics
            val conf = res.configuration
            conf.locale = locale
            res.updateConfiguration(conf, dm)

    }
        fun languageIntentDone() {
            _language.value = false
        }





}