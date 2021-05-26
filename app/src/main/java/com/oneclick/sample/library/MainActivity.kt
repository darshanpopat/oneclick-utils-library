package com.oneclick.sample.library

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.oneclick.utils.library.AppDateFormatUtils
import com.oneclick.utils.library.logA
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val calendar = Calendar.getInstance()
        logA(
            MyApplication.appDateFormatUtils.formatDate(
                calendar.time,
                AppDateFormatUtils.DATE_FORMAT_DATE
            )
        )

    }

}