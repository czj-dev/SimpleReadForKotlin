package com.rank.basiclib.application

import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/29
 *     desc  :
 * </pre>
 */
@Singleton
class ActivityLifecycle @Inject constructor() : Application.ActivityLifecycleCallbacks {

    @Inject
    lateinit var appManager: AppManager

    @Inject
    lateinit var fragmentLifecycleCallbacks: FragmentManager.FragmentLifecycleCallbacks

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        appManager.addActivity(activity)

        if (activity is AppCompatActivity) {
            registerFragmentCallback(activity)
        }
    }

    private fun registerFragmentCallback(activity: AppCompatActivity) {
        activity.supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentLifecycleCallbacks, true)
    }

    override fun onActivityStarted(activity: Activity?) {

    }

    override fun onActivityResumed(activity: Activity?) {
        appManager.currentActivity = activity
    }

    override fun onActivityPaused(activity: Activity?) {

    }


    override fun onActivityStopped(activity: Activity?) {
        if (appManager.currentActivity == activity) {
            appManager.currentActivity = null
        }
    }

    override fun onActivityDestroyed(activity: Activity?) {
        appManager.removeActivity(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {

    }
}