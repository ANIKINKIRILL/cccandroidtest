package com.anikinkirill.cccandroidtest.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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

        val json = getJson(Constants.json)
        json?.let {
            populateDatabase(it)
        } ?: Toast.makeText(this, "Cannot parse JSON", Toast.LENGTH_LONG).show()

        makeFragmentTransaction(MainFragment())

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

        mainViewModel.insertPerson(person).observe(this) {
            if(it > 0) {
                Log.d(TAG, "populateDatabase: person is successfully inserted")
            }else{
                Log.d(TAG, "populateDatabase: error inserting person...")
            }
        }
        mainViewModel.insertEstimate(estimate)
    }

    private fun makeFragmentTransaction(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit()
    }

}