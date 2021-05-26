package com.oneclick.utils.library

import android.app.Application
import android.content.Context
import android.location.LocationManager
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import java.util.regex.Pattern

class AppUtils(var application: Application) {

    companion object {
        fun newInstance(application: Application): AppUtils {
            return AppUtils(application)
        }

        /**
         * Return the validity of Email Address
         */
        fun isEmailValid(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

    }

    fun hideKeyboard(activity: AppCompatActivity) {
        val inputManager: InputMethodManager =
            application.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(
            activity.currentFocus?.windowToken,
            InputMethodManager.SHOW_FORCED
        )
    }

    /**
     * Return the availability of GooglePlayServices
     */
//    fun isGooglePlayServicesAvailable(): Boolean {
//        val googleApiAvailability = GoogleApiAvailability.getInstance()
//        val status = googleApiAvailability.isGooglePlayServicesAvailable(application.baseContext)
//        if (status != ConnectionResult.SUCCESS) {
//            return false
//        }
//        return true
//    }

    val PASSWORD = Pattern.compile(
        "^" + "(?=.*[0-9])" +  //at least 1 digit
                "(?=.*[a-z])" +  //at least 1 lower case letter
                "(?=.*[A-Z])" +  //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +  //any letter
                "(?=.*[@#$%^&+=])" +  //at least 1 special character
                "(?=\\S+$)" +  //no white spaces
                ".{4,}" +  //at least 4 characters
                "$"
    )

    fun isPasswordValid(password: String): Boolean {
        return PASSWORD.matcher(password).matches()
    }

    fun checkLocationStatus(): Boolean {
        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var isGpsEnabled = false
        var isNetworkEnabled = false

        try {
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (e: Exception) {
        }

        try {
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (e: Exception) {
        }

        return isGpsEnabled || isNetworkEnabled
    }

}