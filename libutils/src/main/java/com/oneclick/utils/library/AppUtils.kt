package com.oneclick.utils.library

import android.app.Application
import android.content.Context
import android.content.IntentSender
import android.location.LocationManager
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.location.LocationManagerCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import java.util.regex.Pattern

class AppUtils(var application: Application) {

    companion object {
        const val requestCheckSettings = 111

        fun newInstance(application: Application): AppUtils {
            return AppUtils(application)
        }

        /**
         * Return the validity of Email Address
         */
        fun isEmailValid(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        /**
         * Author : Darshan Popat
         * Date : 19 April 2021
         * Description : Click listener of resend code text to call API to Re-send the OTP email.
         */
        fun isLocationEnabled(context: Context): Boolean {
            val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            return LocationManagerCompat.isLocationEnabled(locationManager)
        }

        /**
         * Author : Darshan Popat
         * Date : 19 April 2021
         * Description : Click listener of resend code text to call API to Re-send the OTP email.
         */
        fun createLocationRequest(activity: AppCompatActivity) {
            val locationRequest = LocationRequest.create().apply {
                interval = 10000
                fastestInterval = 5000
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

            val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)

            val client: SettingsClient = LocationServices.getSettingsClient(activity)
            val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

            task.addOnFailureListener { exception ->
                if (exception is ResolvableApiException) {
                    // Location settings are not satisfied, but this can be fixed
                    // by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        exception.startResolutionForResult(
                            activity,
                            requestCheckSettings
                        )
                    } catch (sendEx: IntentSender.SendIntentException) {
                        // Ignore the error.
                    }
                }
            }
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

}