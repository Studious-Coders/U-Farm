package com.example.u_farm.profile.editprofile

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.Application
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.u_farm.database.AuthRepository
import com.example.u_farm.model.U_Farm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.N)
class EditProfileViewModel(application: Application, activity: Activity): ViewModel() {
    private var alert: AlertDialog.Builder
    var _image= MutableLiveData<Boolean>()
    val image: LiveData<Boolean>
        get()=_image

    private var _spinner= MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean>
        get()=_spinner

    private var _upload= MutableLiveData<Boolean>()
    val upload: LiveData<Boolean>
        get()=_upload

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

    fun updateData(uid:String,username:String,email:String,password:String,phoneNumber:String,profilePicture:String,job:String,location:String,language:String){
        viewModelScope.launch{
            val ufarm= U_Farm(uid,username,email,password,phoneNumber,profilePicture,job,location,language,"")
            upload(ufarm)
            upload1()
         _spinner.value=true

        }

    }
    private suspend fun upload(uFarm: U_Farm){
        withContext(Dispatchers.IO){
            authRepository.setUserData(uFarm)
        }
    }

    private suspend fun uploadImageToFirebase(uri: Uri){
        withContext(Dispatchers.IO){
            Log.d("DefaultProfile",uri.toString())
            authRepository.uploadImageToFirebaseStorage(uri,"images")
        }
    }

    fun upload1(){
        viewModelScope.launch {
            if(setImage.value!=null) {
            setProifleImage(setImage.value.toString())
                _upload.value=true
            }
        }
 }

    private suspend fun setProifleImage(string:String){
        withContext(Dispatchers.IO){
            authRepository.singleRecord(string,"profilePicture")
        }
    }
    fun function(){
        _upload.value=false
         _spinner.value=false
    }

    fun imageFormatingDone(dp: Uri){
        viewModelScope.launch {
            uploadImageToFirebase(dp)
        }
    }


    fun updateJob(string: String){
        viewModelScope.launch {
            updateJob1(string)
        }
    }

   private suspend fun updateJob1(job:String){
       withContext(Dispatchers.IO){
           authRepository.singleRecord(job,"job")
       }
   }


    fun jobSelection(){
       alert.setTitle("Choose a job title")
        val options= arrayOf("Farmer","Expert","Other")

       alert.setItems(options) { dialog, which ->
           dialog.dismiss()
           when (which) {
               0 ->  {
                   updateJob(options[0])
               }
               1 ->  {
                   updateJob(options[1])
               }
               2 ->{
                   updateJob(options[2])
               }
           }
       }
       alert.show()
   }
}
