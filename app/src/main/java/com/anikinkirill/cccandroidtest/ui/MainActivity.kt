package com.anikinkirill.cccandroidtest.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.anikinkirill.cccandroidtest.Constants
import com.anikinkirill.cccandroidtest.R
import com.anikinkirill.cccandroidtest.model.Estimate
import com.anikinkirill.cccandroidtest.model.Person
import com.anikinkirill.cccandroidtest.util.Resource.Status.*
import com.anikinkirill.cccandroidtest.util.showProgressBar
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
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

    }

    /**
     *  Simulate getting json from server. In this case use
     *  Gson library [populateDatabase]
     *  In real world project should use Retrofit
     *  with GsonConverterLibrary
     *
     *  @param json     json from Rest Api
     */

    private fun getJson(json: String) : JSONObject? {
        return try {
            JSONObject(json)
        }catch (throwable: Throwable) {
            Log.d(TAG, "getJson: Cannot parse JSON")
            null
        }
    }

    /**
     * Populate local database with data from json
     * Get objects from json and try inserting them to database
     * If object cannot be inserted (some error happened), application
     * will know about it
     *
     * See [com.anikinkirill.cccandroidtest.repositories.PersonRepository]
     * See [com.anikinkirill.cccandroidtest.repositories.EstimateRepository]
     *
     * When error happens, value is -1. So we check it here down below
     * Otherwise, returns successful result
     *
     * Regarding Google Best Practices we should use Resource wrappers
     * to understand state of process (LOADING, SUCCESS or ERROR)
     *
     * @param json     json to be extracted
     */

    private fun populateDatabase(json: JSONObject) {
        val gson = GsonBuilder().create()
        val person = gson.fromJson(json.getJSONObject("person").toString(), Person::class.java)
        val estimate = gson.fromJson(json.getJSONObject("estimate").toString(), Estimate::class.java)

        progress_bar.visibility = View.VISIBLE

        mainViewModel.insertPerson(person).observe(this) {
            it?.let { resource ->  
                when(resource.status) {
                    is SUCCESS -> {
                        Log.d(TAG, "populateDatabase: person is successfully inserted")
                        showProgressBar(false, progress_bar)
                        makeFragmentTransaction(MainFragment())
                    }
                    is ERROR -> {
                        showProgressBar(false, progress_bar)
                        Log.d(TAG, "populateDatabase: error inserting person...")
                    }
                    is LOADING -> {
                        showProgressBar(true, progress_bar)
                    }
                }
            }
        }

        mainViewModel.insertEstimate(estimate).observe(this) {
            if(it > 0) {
                Log.d(TAG, "populateDatabase: estimate is successfully inserted")
            }else{
                Log.d(TAG, "populateDatabase: error inserting estimate...")
            }
        }
    }

    private fun makeFragmentTransaction(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_container, fragment).commit()
    }

}