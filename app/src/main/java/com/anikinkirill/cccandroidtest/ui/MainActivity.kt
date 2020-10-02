package com.anikinkirill.cccandroidtest.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.anikinkirill.cccandroidtest.Constants
import com.anikinkirill.cccandroidtest.R
import com.anikinkirill.cccandroidtest.model.Estimate
import com.anikinkirill.cccandroidtest.model.Person
import com.google.gson.GsonBuilder
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        subscribeObservers()

        val json = getJson(Constants.json)
        json?.let {
            populateDatabase(it)
        } ?: Toast.makeText(this, "Cannot parse JSON", Toast.LENGTH_LONG).show()

    }

    private fun getJson(json: String) : JSONObject? {
        return try {
            JSONObject(json)
        }catch (throwable: Throwable) {
            Log.d(TAG, "getJson: Cannot parse JSON")
            null
        }
    }

    private fun populateDatabase(json: JSONObject) {
        val gson = GsonBuilder().create()
        val person = gson.fromJson(json.getJSONObject("person").toString(), Person::class.java)
        val estimate = gson.fromJson(json.getJSONObject("estimate").toString(), Estimate::class.java)

        mainViewModel.insertPerson(person)
        mainViewModel.insertEstimate(estimate)
    }

    private fun subscribeObservers() {
        mainViewModel.getPersonById(Constants.personId).observe(this) {
            it?.let {
                Log.d(TAG, "subscribeObservers: ${it.email}")
            }
        }

        mainViewModel.getEstimateById(Constants.estimateId).observe(this) {
            it?.let {
                Log.d(TAG, "subscribeObservers: ${it.company}")
            }
        }

    }

}