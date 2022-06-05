package com.example.u_farm.database

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.u_farm.model.Problem
import com.example.u_farm.model.Solution
import com.example.u_farm.model.U_Farm
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import java.util.*

class AuthRepository(application: Application) {
    private companion object {
        private const val TAG = "signup"
        private const val RC_SIGN_IN = 78
    }

    var auth: FirebaseAuth
    private var firebaseDatabase: FirebaseDatabase
    private var reference: DatabaseReference
    var reference1: DatabaseReference
    var reference2: DatabaseReference
    private var application: Application
    var firebaseUserAuthRepository = MutableLiveData<FirebaseUser?>()
    private var userLoggedAuthRepository = MutableLiveData<Boolean?>()
    private var setUserDataRepository = MutableLiveData<Boolean?>()
    private var getUserDataRepository = MutableLiveData<U_Farm?>()
    private var getDataRepository = MutableLiveData<U_Farm?>()
    private var getProblemRepository = MutableLiveData<Problem?>()
    private var getSolutionRepository = MutableLiveData<Solution?>()
    private var singleRecordDataRepository = MutableLiveData<Boolean?>()
    private var uploadedDataRepository = MutableLiveData<String?>()
    private var setSolutionDataRepository = MutableLiveData<Boolean?>()
    private var setProblemDataRepository = MutableLiveData<Boolean?>()
    private var storage: FirebaseStorage
    private var ProblemDataMutableLiveDataList = MutableLiveData<MutableList<Problem?>>()
    private var SolutionDataMutableLiveDataList = MutableLiveData<MutableList<Solution?>>()
    var problemList = mutableListOf<Problem?>()
    var solutionList = mutableListOf<Solution?>()
    private var gso: GoogleSignInOptions
    private var googleSignInClient: GoogleSignInClient

    init {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("492960336873-kpmq8gmn37riibaoasms8h9ld5s8r6qo.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(application, gso)
        this.application = application
        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference("UFARMDB")
        reference1 = firebaseDatabase.getReference("PROBLEM").push()
        reference2 = firebaseDatabase.getReference("SOLUTION").push()
        storage = FirebaseStorage.getInstance()
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser != null) {
            firebaseUserAuthRepository.postValue(auth.currentUser)
        }
    }

    fun getFirebaseUserMutableLiveData(): MutableLiveData<FirebaseUser?> {
        return firebaseUserAuthRepository
    }

    fun getUserLoggedMutableLiveData(): MutableLiveData<Boolean?> {
        return userLoggedAuthRepository
    }

    fun setUserDataMutableLiveData(): MutableLiveData<Boolean?> {
        return setUserDataRepository
    }

    fun setSolutionDataMutableLiveData(): MutableLiveData<Boolean?> {
        return setSolutionDataRepository
    }

    fun setProblemDataMutableLiveData(): MutableLiveData<Boolean?> {
        return setProblemDataRepository
    }

    fun getUserDataMutableLiveData(): MutableLiveData<U_Farm?> {
        return getUserDataRepository
    }

    fun getDataMutableLiveData(): MutableLiveData<U_Farm?> {
        return getDataRepository
    }

    fun getProblemMutableLiveData(): MutableLiveData<Problem?> {
        return getProblemRepository
    }


    fun getSolutionMutableLiveData(): MutableLiveData<Solution?> {
        return getSolutionRepository
    }

    fun ProblemDataMutableLiveDataList(): MutableLiveData<MutableList<Problem?>> {
        return ProblemDataMutableLiveDataList
    }

    fun SolutionDataMutableLiveDataList(): MutableLiveData<MutableList<Solution?>> {
        return SolutionDataMutableLiveDataList
    }

    fun singleRecordDataMutuableLiveData(): MutableLiveData<Boolean?> {
        return singleRecordDataRepository
    }

    fun uploadedDataMutuableLiveData(): MutableLiveData<String?> {
        return uploadedDataRepository
    }

    /** Authentication Function**/

    //Google SignIn
    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                firebaseUserAuthRepository.postValue(auth.currentUser)
                val user = auth.currentUser
                val ufarm = U_Farm(
                    user!!.uid,
                    user.displayName.toString(),
                    user.email.toString(),
                    "",
                    user.phoneNumber.toString(),
                    user.photoUrl.toString()
                )
                setUserData(ufarm)
            }
        }
    }

    //Register using Email Id
    fun register(username: String, email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                application,
                "Please enter you email address or password",
                Toast.LENGTH_LONG
            )
                .show()
            return
        }
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                firebaseUserAuthRepository.postValue(auth.currentUser)
                val ufarm = U_Farm(auth.currentUser!!.uid, username, email, password)
                setUserData(ufarm)
                Log.d("SignUp", "${it.result?.user?.uid}")
            }

            .addOnFailureListener {
                Toast.makeText(application, "${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    //Login with Email
    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                application,
                "Please enter you email address or password",
                Toast.LENGTH_LONG
            )
                .show()
            return
        }
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (!it.isSuccessful) return@addOnCompleteListener
                firebaseUserAuthRepository.postValue(auth.currentUser)
                Log.d("SignIn", "${it.result?.user?.uid}")
            }

            .addOnFailureListener {
                Toast.makeText(application, "${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    //Logout From the App
    fun signOut() {
        auth.signOut()
        userLoggedAuthRepository.postValue(true)
    }

    /**U_Farm Model Function in Firebase**/

    //SetUser Data into the Model U_Farm
    fun setUserData(ufarm: U_Farm) {
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
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

    //Change a singleRecord in the U_Farm Model in Firebase
    fun singleRecord(data: String, parameter: String) {
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
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

    //Get user data from the U_Farm Model
    fun getUserData() {
        val userData: Query = firebaseDatabase.getReference("/UFARMDB/${auth.currentUser?.uid}")
        userData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val uFarm = snapshot.getValue(U_Farm::class.java)
                if (uFarm != null) {
                    getUserDataRepository.postValue(uFarm)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                getUserDataRepository.postValue(null)
            }
        })
    }

    /**Problem Model Function**/

    //Make a list of Problems to display in the recyclerView
    fun ProblemDataList() {
        val ref = firebaseDatabase.getReference("PROBLEM")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for  (postSnapshot in snapshot.children) {
                    val problem = postSnapshot.getValue(Problem::class.java)
                    if (problem != null) {
                        problemList.add(problem)
                    }
                }
                ProblemDataMutableLiveDataList.postValue(problemList)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    //Changes a single record in Problem Model in Firebase
    fun singleRecordProblem(data: String, parameter: String) {
        reference1.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (auth.currentUser?.uid != null) {
                    reference1.child("/$parameter/").setValue(data)
                }
                singleRecordDataRepository.postValue(true)
            }

            override fun onCancelled(error: DatabaseError) {
                singleRecordDataRepository.postValue(false)
            }
        })
    }

    //Set User data in the Problem Model
    fun setProblemData(problem: Problem) {
        reference1.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (auth.currentUser?.uid != null) {
                    reference1.setValue(problem)
                }
                setProblemDataRepository.postValue(true)
            }

            override fun onCancelled(error: DatabaseError) {
                setProblemDataRepository.postValue(false)
            }
        })
    }

    //Get a single problem data by matching its id
    fun getProblem(problemId: String) {
        val userData: Query = firebaseDatabase.getReference("/PROBLEM/${problemId}")
        userData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val problem = snapshot.getValue(Problem::class.java)
                if (problem != null) {
                    getProblemRepository.postValue(problem)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    //update a single record in Problem Model
    fun updateProblem(data: String, uid: String, parameter: String) {
        val ref = firebaseDatabase.getReference("PROBLEM")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val problem = postSnapshot.getValue(Problem::class.java)
                    if (problem?.userUid == uid) {
                        ref.child("/$parameter").setValue(data)
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }


    /**Solution Model Function**/

    //Make a list of Solutions to display in the recyclerView
    fun SolutionDataList(problemUid: String) {
        val ref = firebaseDatabase.getReference("SOLUTION")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val solution = postSnapshot.getValue(Solution::class.java)
                    if (solution?.problemUid == problemUid) {
                        solutionList.add(solution)
                    }
                }
                SolutionDataMutableLiveDataList.postValue(solutionList)
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    //Get a single solution data by matching its id
    fun getSolution(solutionId: String) {
        val userData: Query = firebaseDatabase.getReference("/SOLUTION/${solutionId}")
        userData.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val solution = snapshot.getValue(Solution::class.java)
                if (solution != null) {
                    getSolutionRepository.postValue(solution)
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

        //Changes a single record in the Solution Model
    fun singleRecordSolution(data: Int, parameter: String, suid: String) {
        val ref = firebaseDatabase.getReference("SOLUTION")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (auth.currentUser?.uid != null) {
                    ref.child("$suid/$parameter").setValue(data)
                }
                singleRecordDataRepository.postValue(true)
            }

            override fun onCancelled(error: DatabaseError) {
                singleRecordDataRepository.postValue(false)
            }
        })
    }

    //To update a single record in the Solution Model
    fun updateSolution(data: String, uid: String, parameter: String) {
        val ref = firebaseDatabase.getReference("SOLUTION")
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val solution = postSnapshot.getValue(Solution::class.java)
                    if (solution?.userUid == uid) {
                        ref.child("/$parameter").setValue(data)
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    //Set the solution data to the Solution Model
    fun setSolutionData(solution: Solution) {
        reference2.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (auth.currentUser?.uid != null) {
                    reference2.setValue(solution)
                }
                setSolutionDataRepository.postValue(true)
            }

            override fun onCancelled(error: DatabaseError) {
                setSolutionDataRepository.postValue(false)
            }
        })
    }

    //Store the Image to the Firebase
    fun uploadImageToFirebaseStorage(image: Uri, folder: String) {
        val ref =
            FirebaseStorage.getInstance().getReference("/$folder/" + UUID.randomUUID().toString())

        val uploadTask = ref.putFile(image)
        val urlTasK =
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
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
