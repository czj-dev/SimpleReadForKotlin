package com.rank.basiclib.application

import android.content.Context
import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import com.rank.basiclib.error.ServiceErrorHandler
import com.rank.basiclib.log.GlobalHttpHandler
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
interface AppLifecycle : GlobalHttpHandler, ServiceErrorHandler {

    fun attachBaseContext(@NonNull base: Context)

    fun onCreate(@NonNull application: BaseApplication)

    /**
     *
     */
    fun onTerminate(@NonNull application: BaseApplication)

    /**
     * 提供该组件的所有需要注册的 ViewModel Provider
     */
    fun returnViewModels(): Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>

    /**
     * 提供以 Class 为 Key 的 AndroidInjector 实例集合
     */
    fun classKeyedInjectorFactories(): Map<Class<*>, Provider<AndroidInjector.Factory<*>>>

    /**
     * 提供以 String 为 Key 的 AndroidInjector 的实例集合
     * 暂时未用
     */
    fun stringKeyedInjectorFactories(): Map<String, Provider<AndroidInjector.Factory<*>>>

}