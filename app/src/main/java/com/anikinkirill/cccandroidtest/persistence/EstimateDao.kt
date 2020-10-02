package com.anikinkirill.cccandroidtest.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anikinkirill.cccandroidtest.model.Estimate
import com.anikinkirill.cccandroidtest.model.Person
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface EstimateDao {

    @Query("SELECT * FROM estimate_table WHERE id = :id")
    fun getEstimateById(id: String) : LiveData<Estimate>

    @Query("SELECT * FROM estimate_table WHERE contact = :contactId")
    fun getEstimateByContactId(contactId: String) : Flowable<Estimate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEstimate(estimate: Estimate) : Single<Long>

}