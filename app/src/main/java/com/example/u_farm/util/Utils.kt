package com.example.u_farm.util

import android.util.Log
import com.chaquo.python.PyObject
import com.chaquo.python.Python
import com.example.u_farm.model.U_Farm
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

//Get user data from the U_Farm Model
var lang:String=""
lateinit var py:Python
lateinit var pyobj:PyObject
fun getUserData() {
    val userData: Query = FirebaseDatabase.getInstance().getReference("/UFARMDB/${FirebaseAuth.getInstance().uid}")
    userData.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val uFarm = snapshot.getValue(U_Farm::class.java)
            if (uFarm != null) {
                lang = uFarm.language
            }else{
                lang="en_US"
            }

        }

        override fun onCancelled(error: DatabaseError) {
             }
    })

    Log.d("Language",lang)
}
fun languageInitial(str:String){

    lang=str
}

fun initialzePython(){
    py = Python.getInstance()
    pyobj = py.getModule("translate")
}




