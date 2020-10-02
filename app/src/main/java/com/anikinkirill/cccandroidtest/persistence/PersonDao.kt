package com.anikinkirill.cccandroidtest.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anikinkirill.cccandroidtest.model.Person
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface PersonDao {

    @Query("SELECT * FROM person_table WHERE id = :id")
    fun getPersonById(id: String) : Flowable<Person>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: Person) : Single<Long>

}