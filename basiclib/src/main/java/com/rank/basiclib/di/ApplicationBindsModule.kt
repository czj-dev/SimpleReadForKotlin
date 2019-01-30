package com.rank.basiclib.di

import android.app.Application
import androidx.fragment.app.FragmentManager
import com.rank.basiclib.application.ActivityLifecycle
import com.rank.basiclib.application.FragmentLifecycle
import dagger.Binds
import dagger.Module

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/22
 *     desc  :
 * </pre>
 */
@Module
abstract class ApplicationBindsModule {

    @Binds
    abstract fun providerActivityLifecycle(activityLifecycle: ActivityLifecycle): Application.ActivityLifecycleCallbacks

    @Binds
    abstract fun providerFragmentLifecycle(fragmentLifecycle: FragmentLifecycle): FragmentManager.FragmentLifecycleCallbacks

}