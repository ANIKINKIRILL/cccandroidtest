package com.anikinkirill.cccandroidtest.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.anikinkirill.cccandroidtest.model.Estimate
import com.anikinkirill.cccandroidtest.persistence.AppDatabase

class EstimateRepository constructor(private val context: Context) {

    private val estimateDao = AppDatabase.getDatabase(context).estimateDao()

    companion object {

        private const val TAG = "EstimateRepository"

        var instance: EstimateRepository? = null
        fun getInstance(context: Context) : EstimateRepository {
            if(instance == null) {
                instance = EstimateRepository(context)
            }
            return instance as EstimateRepository
        }
    }

    fun getEstimateById(id: String) : LiveData<Estimate> = estimateDao.getEstimateById(id)

    suspend fun insertEstimate(estimate: Estimate) {
        estimateDao.insertEstimate(estimate)
    }

}