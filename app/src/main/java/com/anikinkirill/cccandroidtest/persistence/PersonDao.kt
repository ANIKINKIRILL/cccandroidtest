package com.anikinkirill.cccandroidtest.persistence

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anikinkirill.cccandroidtest.model.Person

@Dao
interface PersonDao {

    @Query("SELECT * FROM person_table WHERE id = :id")
    fun getPersonById(id: String) : LiveData<Person>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPerson(person: Person)

}