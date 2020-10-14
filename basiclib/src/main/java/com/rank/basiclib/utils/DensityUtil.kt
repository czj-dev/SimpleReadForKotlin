package com.rank.basiclib.utils

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.os.Bundle
import com.rank.basiclib.ext.application

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2018/7/27
 *     desc  :
 * </pre>
 */
class DensityUtil {

    companion object {

        private var compatDensity: Float? = null

        /**
         * scaleDensity 是用户字体对应的density 可能因为用户切换系统设置而变化
         */
        private var scaleCompatDensity: Float? = null

        @JvmStatic
        fun dp2px(value: Float) = value * application().resources.displayMetrics.density + 0.5f

        @JvmStatic
        fun px2dp(value: Float) = value / application().resources.displayMetrics.density + 0.5f

        @JvmStatic
        fun sp2px(value: Float) = (value * application().resources.displayMetrics.density + 0.5f).toInt()

        @JvmStatic
        fun screamWidth() = application().resources.displayMetrics.widthPixels

        /**
         * 根据屏幕的宽度来动态调整 DisplayMetrics 适配相关的几个变量达到屏幕适配的目的
         */
        fun setCustomDensity(application: Application, width: Float = 375f) {
            if (compatDensity == null) {
                val displayMetrics = application.resources.displayMetrics
                compatDensity = displayMetrics.density
                scaleCompatDensity = displayMetrics.scaledDensity
                application.registerComponentCallbacks(ScaleDensityComponentCallback(application, scaleCompatDensity!!))
            }
            application.registerActivityLifecycleCallbacks(ActivityChangedDensityCallback(width))
        }
    }

    /**
     * 用户字体切换的时候的时候会导致scaleDensity修改，监听字体切换，即使刷新
     */
    class ScaleDensityComponentCallback(private val application: Application, private var scaleDensity: Float) :
        ComponentCallbacks {
        override fun onConfigurationChanged(newConfig: Configuration?) {
            if (newConfig != null && newConfig.fontScale > 0) {
                scaleDensity = application.resources.displayMetrics.scaledDensity
            }
        }

        override fun onLowMemory() {
        }
    }

    /**
     * 在 Activity 的 onCreate 阶段调整 application 和 Activity 的适配
     */
    class ActivityChangedDensityCallback(private val width: Float) : Application.ActivityLifecycleCallbacks {

        override fun onActivityCreated(activity: Activity?, p1: Bundle?) {
            val displayMetrics = application().resources.displayMetrics
            val targetDensity = displayMetrics.widthPixels / width
            val targetScaleDensity = targetDensity * (scaleCompatDensity!! / compatDensity!!)
            val targetDensityDpi = (160 * targetDensity).toInt()
            displayMetrics.density = targetDensity
            displayMetrics.scaledDensity = targetScaleDensity
            displayMetrics.densityDpi = targetDensityDpi

            activity?.resources?.displayMetrics?.apply {
                density = targetDensity
                scaledDensity = targetScaleDensity
                densityDpi = targetDensityDpi
            }
        }

        override fun onActivityPaused(p0: Activity?) {
            //ignore
        }

        override fun onActivityResumed(p0: Activity?) {
            //ignore
        }

        override fun onActivityStarted(p0: Activity?) {
            //ignore
        }

        override fun onActivityDestroyed(p0: Activity?) {
            //ignore
        }

        override fun onActivitySaveInstanceState(p0: Activity?, p1: Bundle?) {
            //ignore
        }

        override fun onActivityStopped(p0: Activity?) {
            //ignore
        }


    }
}