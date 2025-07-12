package com.example.u_farm.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.u_farm.localdatabase.UFarmDatabase.Companion.getInstance
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {
    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val database = getInstance(applicationContext)
        val repository = LocalRepository(database)
        return try {
            repository.getProblems()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }
    }
}