package com.oneclick.sample.library

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.oneclick.utils.library.AppDateFormatUtils
import com.oneclick.utils.library.AppUtils
import com.oneclick.utils.library.logA

class MainActivity : AppCompatActivity() {

    val appUtils = AppUtils(application)
    val appDateFormatUtils = AppDateFormatUtils(application)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        logA()

    }

}