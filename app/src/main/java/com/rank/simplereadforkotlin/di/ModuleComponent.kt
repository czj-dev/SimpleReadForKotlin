package com.rank.simplereadforkotlin.di

import android.app.Activity
import androidx.lifecycle.ViewModel
import com.rank.basiclib.di.AppComponent
import com.rank.basiclib.scope.ActivityScope
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Provider

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/23
 *     desc  :
 * </pre>
 */
@ActivityScope
@Component(modules = [BindViewModule::class, BindActivityModule::class], dependencies = [AppComponent::class])
interface ModuleComponent {

    fun viewModules(): Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>

    fun activitys(): Map<Class<out Activity>, Provider<AndroidInjector.Factory<out Activity>>>

    fun inject(app: AppLifecycleImpl)
}