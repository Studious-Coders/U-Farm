package com.example.u_farm.util

import android.util.Log
import com.example.u_farm.home.HomeViewModel
import com.example.u_farm.model.U_Farm
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

//Get user data from the U_Farm Model
var lang:String=""

fun getUserData() {
    val userData: Query = FirebaseDatabase.getInstance().getReference("/UFARMDB/${FirebaseAuth.getInstance().uid}")
    userData.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val uFarm = snapshot.getValue(U_Farm::class.java)
                lang=uFarm!!.language
          }

        override fun onCancelled(error: DatabaseError) {

          }
    })

    Log.d("Priya",lang)
}
fun languageInitial(str:String){
    lang=str
}
