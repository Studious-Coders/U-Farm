package com.example.u_farm.profile.editprofile

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.app.DatePickerDialog
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.model.U_Farm
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_editprofile.*
import java.util.*

@RequiresApi(Build.VERSION_CODES.N)
class EditProfileViewModel(application: Application, activity: Activity): ViewModel() {
    var _image= MutableLiveData<Boolean>()
    val image: LiveData<Boolean>
        get()=_image

    private var _spinner= MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean>
        get()=_spinner

    private var authRepository: AuthRepository

    val getData: LiveData<U_Farm?>
        get()=authRepository.getUserDataMutableLiveData()

    val setData: LiveData<Boolean?>
        get()=authRepository.setUserDataMutableLiveData()

    val setImage:LiveData<String?>
        get()=authRepository.uploadedDataMutuableLiveData()

    @SuppressLint("StaticFieldLeak")
    var act:Activity

    init {
        authRepository = AuthRepository(application)
        authRepository.getUserData()
        act = activity

    }



    fun imageFormating() {
        _image.value=true
    }
    var str:String?=null



    fun updateData(username:String,email:String,password:String,phoneNumber:String,job:String,location:String){
        if(setImage.value!=null) {
            str=setImage.value.toString()
        }else{
            str= getData.value?.profilePicture
        }
        val ufarm= U_Farm(username,email,password,phoneNumber,str!!,job,location,"","")
        authRepository.setUserData(ufarm)
        _spinner.value=true
    }

    fun function(){
        authRepository.getUserData()
        _spinner.value=false
    }


    fun imageFormatingDone(dp: Uri){
        authRepository.uploadImageToFirebaseStorage(dp,"images")
        _image.value=false
    }





}
