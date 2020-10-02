package com.anikinkirill.cccandroidtest.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import com.anikinkirill.cccandroidtest.Constants.Companion.INSERT_FAILURE
import com.anikinkirill.cccandroidtest.Constants.Companion.timeDelay
import com.anikinkirill.cccandroidtest.Constants.Companion.timeUnit
import com.anikinkirill.cccandroidtest.model.Person
import com.anikinkirill.cccandroidtest.persistence.AppDatabase
import com.anikinkirill.cccandroidtest.util.Resource
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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

    fun getPersonById(id: String) : LiveData<Person> {
        return LiveDataReactiveStreams.fromPublisher(
            personDao
            .getPersonById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .onErrorReturn {
                Person("-1")
            }
            .map {
                it
            }
        )
    }

    fun insertPerson(person: Person) : Flowable<Resource<Int>> {
        return personDao
            .insertPerson(person)
            .delaySubscription(timeDelay, timeUnit)     // for testing purposes
            .map {
                it.toInt()
            }
            .onErrorReturn {
                -1
            }
            .map {
                if(it > 0) {
                    Resource.Success(it)
                }
                else {
                    Resource.Error(it, INSERT_FAILURE)
                }
            }
            .subscribeOn(Schedulers.io())
            .toFlowable()
    }

}