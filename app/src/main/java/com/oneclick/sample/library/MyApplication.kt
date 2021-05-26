package com.oneclick.sample.library

import android.app.Application
import com.oneclick.utils.library.AppDateFormatUtils
import com.oneclick.utils.library.AppUtils

class MyApplication : Application() {

    companion object {
        lateinit var application: Application
        lateinit var appUtils: AppUtils
        lateinit var appDateFormatUtils: AppDateFormatUtils
    }

    override fun onCreate() {
        super.onCreate()

        application = this
        appUtils = AppUtils(application)
        appDateFormatUtils = AppDateFormatUtils(application)


    }

}