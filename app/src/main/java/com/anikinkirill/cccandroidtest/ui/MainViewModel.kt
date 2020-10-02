package com.anikinkirill.cccandroidtest.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.anikinkirill.cccandroidtest.model.Estimate
import com.anikinkirill.cccandroidtest.model.Person
import com.anikinkirill.cccandroidtest.repositories.EstimateRepository
import com.anikinkirill.cccandroidtest.repositories.PersonRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val personRepository = PersonRepository.getInstance(application)
    private val estimateRepository = EstimateRepository.getInstance(application)

    fun insertPerson(person: Person) = viewModelScope.launch(Dispatchers.IO) {
        personRepository.insertPerson(person)
    }

    fun insertEstimate(estimate: Estimate) = viewModelScope.launch(Dispatchers.IO) {
        estimateRepository.insertEstimate(estimate)
    }

    fun getPersonById(id: String) : LiveData<Person> = personRepository.getPersonById(id)

    fun getEstimateById(id: String) : LiveData<Estimate> = estimateRepository.getEstimateById(id)

    fun getEstimateByContactId(contactId: String) : LiveData<Estimate> = estimateRepository.getEstimateByContactId(contactId)

}