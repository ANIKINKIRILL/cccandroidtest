package com.anikinkirill.cccandroidtest.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.anikinkirill.cccandroidtest.Constants
import com.anikinkirill.cccandroidtest.R
import com.anikinkirill.cccandroidtest.model.Estimate
import com.anikinkirill.cccandroidtest.model.Person

class MainFragment : Fragment() {

    companion object {
        private const val TAG = "MainFragment"
    }

    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
    }

    private fun setPersonData(person: Person) {
        Log.d(TAG, "setPersonData: ${person.first_name} ${person.last_name}")
        // TODO("SET PERSON DATA TO UI WIDGETS")
    }

    private fun setEstimateData(estimate: Estimate) {
        Log.d(TAG, "setEstimateData: ${estimate.company}")
        // TODO("SET ESTIMATE DATA TO UI WIDGETS")
    }

    private fun subscribeObservers() {
        mainViewModel.getPersonById(Constants.personId).removeObservers(this)
        mainViewModel.getPersonById(Constants.personId).observe(viewLifecycleOwner) {
            it?.let { person ->
                setPersonData(person)
                mainViewModel.getEstimateByContactId(person.id).observe(viewLifecycleOwner) { estimate ->
                    estimate?.let {
                        setEstimateData(it)
                    }
                }
            }
        }

    }

}