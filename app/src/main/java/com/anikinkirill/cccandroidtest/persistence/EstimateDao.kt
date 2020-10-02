package com.anikinkirill.cccandroidtest.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anikinkirill.cccandroidtest.model.Estimate
import com.anikinkirill.cccandroidtest.model.Person

@Dao
interface EstimateDao {

    @Query("SELECT * FROM estimate_table WHERE id = :id")
    fun getEstimateById(id: String) : LiveData<Estimate>

    @Query("SELECT * FROM estimate_table WHERE contact = :contactId")
    fun getEstimateByContactId(contactId: String) : LiveData<Estimate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEstimate(estimate: Estimate)

}