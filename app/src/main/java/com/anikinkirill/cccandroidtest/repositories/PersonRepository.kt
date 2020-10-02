package com.anikinkirill.cccandroidtest.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import com.anikinkirill.cccandroidtest.model.Person
import com.anikinkirill.cccandroidtest.persistence.AppDatabase

class PersonRepository constructor(private val context: Context) {

    private val personDao = AppDatabase.getDatabase(context).personDao()

    companion object {

        private const val TAG = "PersonRepository"

        var instance: PersonRepository? = null
        fun getInstance(context: Context) : PersonRepository {
            if(instance == null) {
                instance = PersonRepository(context)
            }
            return instance as PersonRepository
        }
    }

    fun getPersonById(id: String) : LiveData<Person> = personDao.getPersonById(id)

    suspend fun insertPerson(person: Person) {
        personDao.insertPerson(person)
    }

}