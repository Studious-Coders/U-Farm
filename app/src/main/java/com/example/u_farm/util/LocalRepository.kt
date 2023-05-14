package com.example.u_farm.util

import com.example.u_farm.localdatabase.Problems
import com.example.u_farm.model.Problem
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.u_farm.localdatabase.UFarmDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LocalRepository(private val database: UFarmDatabase) {
    private var ProblemDataMutableLiveDataList = MutableLiveData<List<Problems?>>()
    val prob: LiveData<List<Problems>> =
        Transformations.map(database.ufarmDatabaseDao.getProblem()) {
            it
        }

    var problem1: MutableList<Problems> = mutableListOf()
    init{

    }

    suspend fun getProblems() {
        withContext(Dispatchers.IO) {
            var viewModelJob = Job()
            val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
            val firebaseDatabase: FirebaseDatabase
            firebaseDatabase = FirebaseDatabase.getInstance()
            val ref = firebaseDatabase.getReference("PROBLEM")
            ref.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (postSnapshot in snapshot.children) {
                        val problem = postSnapshot.getValue(Problem::class.java)
                        if (problem != null) {

                            problem1.add(Problems(problem.problemUid, problem))
                        }
                    }
                    coroutineScope.launch {
                        database.ufarmDatabaseDao.insertAllProblems(problem1)
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }
}

