package com.neonankiti.workmanagerdaggersample

import android.app.Application
import androidx.work.Configuration
import androidx.work.WorkManager

class BisonApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        val factory: AppWorkerFactory = DaggerBisonAppComponent.create().factory()
        WorkManager.initialize(this, Configuration.Builder().setWorkerFactory(factory).build())
    }

}