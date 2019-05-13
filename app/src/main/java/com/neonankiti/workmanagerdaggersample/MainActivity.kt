package com.neonankiti.workmanagerdaggersample

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.time.Duration

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WorkManager.getInstance().enqueueUniquePeriodicWork(
            "MainActivity",
            ExistingPeriodicWorkPolicy.REPLACE,
            PeriodicWorkRequestBuilder<BisonWorker>(Duration.ofMinutes(15)).build()
        )
    }
}

