package com.neonankiti.workmanagerdaggersample

import android.content.Context
import android.util.Log
import androidx.work.ListenableWorker
import androidx.work.Worker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.google.firebase.perf.FirebasePerformance
import com.squareup.inject.assisted.Assisted
import com.squareup.inject.assisted.AssistedInject
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

class Repository @Inject constructor()

class BisonWorker @AssistedInject constructor(
    @Assisted private val appContext: Context,
    @Assisted private val params: WorkerParameters,
    private val repository: Repository
) : Worker(appContext, params) {

    override fun doWork(): Result {
        Log.d(TAG, "Hello world!")
        Log.d(TAG, "$repository is injected")
        val trace = FirebasePerformance.getInstance().newTrace("WorkerExecuted")
        trace.start()
        trace.putAttribute("Class", "BisonWorker")
        val format = SimpleDateFormat("yyyy/MM/dd hh:mm:ss")
        trace.putAttribute("datetime", format.format(Date()))
        trace.stop()
        return Result.success()
    }

    @AssistedInject.Factory
    interface Factory : ChildWorkerFactory

    companion object {
        private const val TAG = "BisonWorker"
    }
}

interface ChildWorkerFactory {
    fun create(appContext: Context, params: WorkerParameters): ListenableWorker
}

class AppWorkerFactory @Inject constructor(
    private val workerFactories: Map<Class<out ListenableWorker>,
            @JvmSuppressWildcards Provider<ChildWorkerFactory>>
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        val foundEntry =
            workerFactories.entries.find { Class.forName(workerClassName).isAssignableFrom(it.key) }
        val factoryProvider = foundEntry?.value
            ?: throw IllegalArgumentException("unknown worker class name: $workerClassName")
        return factoryProvider.get().create(appContext, workerParameters)
    }
}
