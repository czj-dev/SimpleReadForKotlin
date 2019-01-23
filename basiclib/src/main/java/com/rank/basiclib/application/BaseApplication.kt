package com.rank.basiclib.application

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.multidex.MultiDex
import com.rank.basiclib.di.*
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import java.util.*
import javax.inject.Provider
import kotlin.collections.HashMap

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/2
 *     desc  :
 * </pre>
 */
open class BaseApplication : Application(), HasActivityInjector {


    private val viewModels: HashMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>> = HashMap()

    private lateinit var appLifecycle: ServiceLoader<AppLifecycle>

    private lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    lateinit var appComponent: AppComponent

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
        appLifecycle = ServiceLoader.load(AppLifecycle::class.java)
        for (appLifecycleObservable in appLifecycle) {
            appLifecycleObservable.attachBaseContext(this)
        }
    }

    override fun onCreate() {
        super.onCreate()
        val environmentModule = EnvironmentModule()
        appComponent = AppInjector.init(this, environmentModule)

        val viewInjectUtils = AndroidInjectorUtils<Activity>()
        for (appLifecycleObservable in appLifecycle) {

            appLifecycleObservable.onCreate(this)

            val moduleViewModels = appLifecycleObservable.returnViewModels()
            this.viewModels.putAll(moduleViewModels)

            val moduleViews = appLifecycleObservable.returnViews()
            viewInjectUtils.putAll(moduleViews)
        }
        environmentModule.factory = AndroidViewModelFactory(viewModels)
        dispatchingAndroidInjector = viewInjectUtils.get()
    }

    override fun activityInjector() = dispatchingAndroidInjector

    override fun onTerminate() {
        super.onTerminate()
        for (appLifecycleObservable in appLifecycle) {
            appLifecycleObservable.onTerminate(this)
        }
    }

}