package com.rank.basiclib.application

import android.app.Activity
import android.content.Context
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import dagger.android.AndroidInjector
import javax.inject.Provider

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/22
 *     desc  :
 * </pre>
 */
interface AppLifecycle {

    fun attachBaseContext(@NonNull base: Context)

    fun onCreate(@NonNull application: BaseApplication)

    fun onTerminate(@NonNull application: BaseApplication)

    fun returnViewModels(): Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>

    fun returnViews(): Map<Class<out Activity>, Provider<AndroidInjector.Factory<out Activity>>>
}