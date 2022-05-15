package com.example.u_farm.database

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.u_farm.model.U_Farm
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import kotlinx.coroutines.NonCancellable.children
import java.util.*


class AuthRepository(application: Application){
    private var auth: FirebaseAuth
    private var firebaseDatabase:FirebaseDatabase
    private var reference:DatabaseReference
    private var application:Application
    private var firebaseUserAuthRepository= MutableLiveData<FirebaseUser?>()
    private var userLoggedAuthRepository=MutableLiveData<Boolean?>()
    private var setUserDataRepository=MutableLiveData<Boolean?>()
    private var getUserDataRepository=MutableLiveData<U_Farm?>()
    private var singleRecordDataRepository=MutableLiveData<Boolean?>()
    private var uploadedDataRepository=MutableLiveData<String?>()
    private var storage:FirebaseStorage
    private var userDataMutableLiveDataList=MutableLiveData<MutableList<U_Farm?>>()
     var userData1=mutableListOf<U_Farm?>()

    init{
        this.application=application
        firebaseDatabase= FirebaseDatabase.getInstance()
        reference=firebaseDatabase.getReference("UFARMDB")
        storage=FirebaseStorage.getInstance()
        auth= FirebaseAuth.getInstance()
        if(auth.currentUser!=null){
            firebaseUserAuthRepository.postValue(auth.currentUser)

        }
    }

    fun getFirebaseUserMutableLiveData(): MutableLiveData<FirebaseUser?>{
        return firebaseUserAuthRepository
    }

    fun getUserLoggedMutableLiveData(): MutableLiveData<Boolean?> {
        return userLoggedAuthRepository
    }

    fun setUserDataMutableLiveData(): MutableLiveData<Boolean?> {
        return setUserDataRepository
    }

    fun getUserDataMutableLiveData(): MutableLiveData<U_Farm?> {
        return getUserDataRepository
    }


    fun userDataMutableLiveDataList(): MutableLiveData<MutableList<U_Farm?>> {
        return userDataMutableLiveDataList
    }


    fun singleRecordDataMutuableLiveData(): MutableLiveData<Boolean?>{
        return singleRecordDataRepository
    }


    fun uploadedDataMutuableLiveData(): MutableLiveData<String?>{
        return uploadedDataRepository
    }



    fun register(username:String,email:String,password:String){
        if(email.isEmpty()||password.isEmpty()){
            Toast.makeText(application, "Please enter you email address or password", Toast.LENGTH_LONG)
                .show()
            return
        }
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if(!it.isSuccessful) return@addOnCompleteListener
                firebaseUserAuthRepository.postValue(auth.currentUser)
                val ufarm=U_Farm(username,email,password)
                setUserData(ufarm)
                Log.d("SignUp", "${it.result?.user?.uid}")
            }

            .addOnFailureListener{
                Toast.makeText(application, "${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    fun login(email:String,password: String){
        if(email.isEmpty()||password.isEmpty()){
            Toast.makeText(application, "Please enter you email address or password", Toast.LENGTH_LONG)
                .show()
            return
        }
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{
                if(!it.isSuccessful) return@addOnCompleteListener
                firebaseUserAuthRepository.postValue(auth.currentUser)
                Log.d("SignIn", "${it.result?.user?.uid}")
            }

            .addOnFailureListener{
                Toast.makeText(application,"${it.message}",Toast.LENGTH_LONG).show()
            }
    }

    fun signOut(){
        auth.signOut()
        userLoggedAuthRepository.postValue(true)

    }

    //UserModel
    fun setUserData(ufarm: U_Farm){
        reference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (auth.currentUser?.uid != null) {
                    reference.child(auth.currentUser!!.uid).setValue(ufarm)
                }
                setUserDataRepository.postValue(true)
            }
            override fun onCancelled(error: DatabaseError) {
                setUserDataRepository.postValue(false)
            }
        })
    }

    //Single Record Changes
    fun singleRecord(data:String,parameter:String){
        reference.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (auth.currentUser?.uid != null) {
                    reference.child("${auth.currentUser?.uid}/$parameter").setValue(data)
                }
                singleRecordDataRepository.postValue(true)
            }
            override fun onCancelled(error: DatabaseError) {
                singleRecordDataRepository.postValue(false)
            }
        })
    }

    fun getUserData(){
        val userData:Query=firebaseDatabase.getReference("/UFARMDB/${auth.currentUser?.uid}")
        userData.addValueEventListener(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val uFarm = snapshot.getValue(U_Farm::class.java)
                if (uFarm != null) {
                    getUserDataRepository.postValue(uFarm)
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    fun getUserDataList(){
    val userData:Query=firebaseDatabase.getReference("/UFARMDB")
    userData.addValueEventListener(object :ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
                        for (postSnapshot in snapshot.children) {
                            val uFarm = postSnapshot.getValue(U_Farm::class.java)
                            if (uFarm != null) {
                                userData1.add(uFarm)
                            }
                        }
                        userDataMutableLiveDataList.postValue(userData1)
                    }
                        override fun onCancelled(error: DatabaseError) {

                }

            })

        }

    fun uploadImageToFirebaseStorage(image: Uri) {
        val ref =FirebaseStorage.getInstance().getReference("/images/" + UUID.randomUUID().toString())

        val uploadTask = ref.putFile(image)
        val urlTasK = uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation ref.downloadUrl

            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result
                    Log.d("AuthRepository", "Downloaded URL: is ${downloadUri}")
                    val downloadUrl = downloadUri.toString()
                    uploadedDataRepository.postValue(downloadUrl)
                } else {
                    uploadedDataRepository.postValue(null)
                }
            }.addOnFailureListener {
                uploadedDataRepository.postValue(null)
            }
    }

}
