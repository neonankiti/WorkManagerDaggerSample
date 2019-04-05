package com.neonankiti.workmanagerdaggersample

import androidx.work.ListenableWorker
import com.squareup.inject.assisted.AssistedInject
import com.squareup.inject.assisted.dagger2.AssistedModule
import dagger.Binds
import dagger.Component
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Component(modules = [AppAssistedInjectModule::class, WorkerBindingModule::class])
interface BisonAppComponent {
    fun factory(): AppWorkerFactory
}

@Module(includes = [AssistedInject_AppAssistedInjectModule::class])
@AssistedModule
abstract class AppAssistedInjectModule {}

@MapKey
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class WorkerKey(val value: KClass<out ListenableWorker>)

@Module
interface WorkerBindingModule {
    @Binds
    @IntoMap
    @WorkerKey(BisonWorker::class)
    fun bindBisonWorker(factory: BisonWorker.Factory): ChildWorkerFactory
}
