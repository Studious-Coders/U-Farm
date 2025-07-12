package com.example.u_farm.profile

import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.content.DialogInterface
import androidx.lifecycle.*
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.model.U_Farm
import com.example.u_farm.util.getUserData
import com.example.u_farm.util.languageInitial
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ProfileViewModel(application: Application, activity: Activity): ViewModel() {
    private var alert: AlertDialog.Builder
    private var alert0: AlertDialog.Builder

    private var _navigateToEditProfile= MutableLiveData<Boolean>()
    val navigateToEditProfile: LiveData<Boolean>
        get()=_navigateToEditProfile

    private var authRepository: AuthRepository

    val loggedUser: LiveData<Boolean?>
        get()=authRepository.getUserLoggedMutableLiveData()

    private val _language= MutableLiveData<String?>()
    val language: LiveData<String?>
        get()=_language

    private val _expert= MutableLiveData<Boolean?>()
    val expert: LiveData<Boolean?>
        get()=_expert



    val setData:LiveData<Boolean?>
        get()=authRepository.singleRecordDataMutuableLiveData()

    val getData: LiveData<U_Farm?>
        get()=authRepository.getUserDataMutableLiveData()

    private val _snackbar= MutableLiveData<Boolean?>()
    val snackbar: LiveData<Boolean?>
        get()=_snackbar

    val value= MediatorLiveData<U_Farm>()

    init {
        authRepository = AuthRepository(application)
        alert = AlertDialog.Builder(activity)
        alert0 = AlertDialog.Builder(activity)
        authRepository.getUserData()
        value.addSource(getData,value::setValue)
        _expert.value=true

    }

    fun expertTickDone(){
        _expert.value=false
    }

    fun func(){
        _snackbar.value=false
    }


    fun function(){
        authRepository.getUserData()
        _expert.value=true
    }

    fun logOutAlertDialogBox(){
        if(authRepository.auth.currentUser!=null) {
            alert.setMessage("Are you sure you want to Signout?")
                .setPositiveButton("YES", DialogInterface.OnClickListener { dialog, which ->
                    authRepository.signOut()
                    getUserData()
                }).setNegativeButton("NO", null)

            val alert1: AlertDialog = alert.create()

            alert.show()
        }else{
            _snackbar.value=true
        }

    }
    fun navigateToEditProfile(){
        if(authRepository.auth.currentUser!=null) {
            _navigateToEditProfile.value = true
        }else{
            _snackbar.value=false
        }
    }

    fun navigateToEditProfileDone(){
        _navigateToEditProfile.value=false
    }




    fun updateLanguage(lang:String){
        viewModelScope.launch {
            languageSet(lang)
        }
    }

    private suspend fun languageSet(language:String){
        withContext(Dispatchers.IO) {
            authRepository.singleRecord(language, "language")

        }
    }
    fun languageIntent() {
        if(authRepository.auth.currentUser!=null) {
            alert0.setTitle("Choose a language")
            val  options = arrayOf("தமிழ்","English","हिन्दी")
            alert0.setItems(options) { dialog, which ->
                dialog.dismiss()

                when (which) {
                    /* execute here your actions */
                    0 -> {
//                setLocale("ta")
                        updateLanguage("ta_IN")
//                _language.value="ta"
                        languageInitial("ta_IN")

                    }
                    1 -> {
//                setLocale("en")
                        updateLanguage("en_US")
//                _language.value="en"
                        languageInitial("en_US")
                    }
                    2 -> {
//                setLocale("hi")
                        updateLanguage("hi_IN")
//                _language.value="hi"
                        languageInitial("hi_IN")
                    }

                }


            }

            alert0.show()

        }else{
            _snackbar.value=false
        }
    }

//    profileViewModel.language.observe(viewLifecycleOwner,Observer{
//        if(it!=null){
//            locale = Locale(it)
//            val dm=resources.displayMetrics
//            val conf = resources.configuration
//            conf.locale = locale
//            resources.updateConfiguration(conf, dm)
//            val intent= Intent(application, HomeActivity::class.java)
//            startActivity(intent)
//            profileViewModel.languageIntentDone()
//            Log.d("Language of App",locale.language)
//        }
//    })


//    fun languageIntentDone() {
//            _language.value = null
//        }





}