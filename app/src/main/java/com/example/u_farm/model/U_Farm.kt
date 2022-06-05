package com.example.u_farm.model

import android.net.Uri

data class U_Farm(
    var uid:String="",
    var username:String="#Username",
    var email:String="",
    var password:String="",
    var phonenumber:String="",
    var profilePicture:String="",
    var job:String="#Farmer",
    var location:String="",
    var language:String="English",
    var verificationDocs:String="",
)

data class Problem(
    var problemUid:String="",
    var userUid:String="",
    var username: String="",
    var profilePicture: String="",
    var problemStatement:String="",
    var diseaseAffectedPlantImage: String="",

    )

data class Solution(
    var solutionUid:String="",
    var problemUid:String="",
    var userUid:String="",
    var username: String="",
    var profilePicture: String="",
    var solutionStatement:String="",
    var rating: Int=0,
    )

data class Comments(
    var commentUid:String="",
    var solutionUid: String="",
    var userUid:String="",
    var username:String="",
    var profilePicture:String="",
    var commentStatement:String="",
    )
