package com.example.u_farm.profile.editprofile

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
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
    private var alert: AlertDialog.Builder
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
        get()=authRepository.singleRecordDataMutuableLiveData()

    val setImage:LiveData<String?>
        get()=authRepository.uploadedDataMutuableLiveData()

    @SuppressLint("StaticFieldLeak")
    var act:Activity

    init {
        authRepository = AuthRepository(application)
        authRepository.getUserData()
        act = activity
        alert = AlertDialog.Builder(activity)
    }

    fun imageFormating() {
        _image.value=true
    }

    var str:String?=null

    var uri:Uri?=null
    fun updateData(uid:String,username:String,email:String,password:String,phoneNumber:String,profilePicture:String,job:String,location:String,language:String){
        _spinner.value=true
        val ufarm= U_Farm(uid,username,email,password,phoneNumber,profilePicture,job,location,language,"")
        authRepository.setUserData(ufarm)
    }

    fun upload(){
        authRepository.uploadImageToFirebaseStorage(uri!!,"images")
       }

    fun upload1(){
     if(setImage.value!=null) {
         str=setImage.value.toString()
     }else{
         str= getData.value?.profilePicture
     }
     authRepository.singleRecord(str!!,"profilePicture")

 }
    fun function(){
        authRepository.getUserData()
         _spinner.value=false
    }

    fun imageFormatingDone(dp: Uri){
        uri=dp
        upload()
    }

    fun jobSelection(){
       alert.setTitle("Choose a job title")
       val options = arrayOf("Farmer","Expert","Other")
       alert.setItems(options) { dialog, which ->
           dialog.dismiss()
           when (which) {
               0 ->  {
                   authRepository.singleRecord(options[0],"job")
               }
               1 ->  {
                   authRepository.singleRecord(options[1],"job")
               }
               2 ->{
                   authRepository.singleRecord(options[2],"job")
               }
           }
       }
       alert.show()
   }
}
