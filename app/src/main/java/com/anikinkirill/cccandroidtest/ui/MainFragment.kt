package com.anikinkirill.cccandroidtest.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.anikinkirill.cccandroidtest.Constants
import com.anikinkirill.cccandroidtest.Constants.Companion.errorEstimate
import com.anikinkirill.cccandroidtest.Constants.Companion.errorPerson
import com.anikinkirill.cccandroidtest.databinding.FragmentMainBinding
import com.anikinkirill.cccandroidtest.model.Estimate
import com.anikinkirill.cccandroidtest.model.Person

class MainFragment : Fragment() {

    companion object {
        private const val TAG = "MainFragment"
    }

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeObservers()
    }

    private fun setPersonData(person: Person) {
        Log.d(TAG, "setPersonData: ${person.first_name} ${person.last_name}")
        binding.person = person
    }

    private fun setEstimateData(estimate: Estimate) {
        Log.d(TAG, "setEstimateData: ${estimate.company}")
        binding.estimate = estimate
    }

    private fun setErrorUserUi(person: Person) {
        binding.person = person
    }

    private fun setErrorEstimateUi(estimate: Estimate) {
        binding.estimate = estimate
    }

    private fun subscribeObservers() {
        mainViewModel.getPersonById(Constants.personId).observe(viewLifecycleOwner) {
            it?.let { person ->
                if(person.id == "-1") {
                    Log.d(TAG, "subscribeObservers: ERROR...")
                    setErrorUserUi(errorPerson)
                }else{
                    setPersonData(person)
                    mainViewModel.getEstimateByContactId(person.id).observe(viewLifecycleOwner) {
                        it?.let { estimate ->
                            if(estimate.id == "-1") {
                                Log.d(TAG, "subscribeObservers: ERROR...")
                                setErrorEstimateUi(errorEstimate)
                            } else {
                                setEstimateData(estimate)
                            }
                        } ?: Log.d(TAG, "MainFragment: estimate is null")
                    }
                }
            } ?: Log.d(TAG, "MainFragment: person is null")
        }
    }

}