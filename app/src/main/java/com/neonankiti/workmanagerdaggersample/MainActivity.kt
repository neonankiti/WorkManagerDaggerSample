package com.neonankiti.workmanagerdaggersample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WorkManager.getInstance().enqueue(
            OneTimeWorkRequestBuilder<BisonWorker>().build()
        )

    }
}

