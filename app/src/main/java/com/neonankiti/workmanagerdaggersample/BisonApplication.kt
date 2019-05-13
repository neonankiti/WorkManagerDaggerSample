package com.neonankiti.workmanagerdaggersample

import android.annotation.SuppressLint
import android.app.Application
import android.provider.Settings
import androidx.work.Configuration
import androidx.work.WorkManager
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.perf.FirebasePerformance
import java.text.SimpleDateFormat
import java.util.*

class BisonApplication : Application() {

    @SuppressLint("SimpleDateFormat", "HardwareIds")
    override fun onCreate() {
        super.onCreate()
        val factory: AppWorkerFactory = DaggerBisonAppComponent.create().factory()
        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(factory).build())
        FirebaseAnalytics.getInstance(this)
            .setUserId(Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID))
        val trace = FirebasePerformance.getInstance().newTrace("ApplicationExecuted")
        trace.start()
        trace.putAttribute("Class", "BisonApplication")
        val format = SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
        trace.putAttribute("datetime", format.format(Date()))
        trace.stop()
    }
}