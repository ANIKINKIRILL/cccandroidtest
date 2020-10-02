package com.anikinkirill.cccandroidtest.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.anikinkirill.cccandroidtest.model.Estimate
import com.anikinkirill.cccandroidtest.model.Person
import com.anikinkirill.cccandroidtest.persistence.AppDatabase
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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

    fun getEstimateByContactId(contactId: String) : LiveData<Estimate> {
        return LiveDataReactiveStreams.fromPublisher(
            estimateDao
                .getEstimateByContactId(contactId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorReturn {
                    Estimate("-1")
                }
                .map {
                    it
                }
        )
    }

    fun insertEstimate(estimate: Estimate) : Flowable<Int> {
        return estimateDao
            .insertEstimate(estimate)
            .map {
                it.toInt()
            }
            .onErrorReturn {
                -1
            }
            .map {
                it
            }
            .subscribeOn(Schedulers.io())
            .toFlowable()
    }

}