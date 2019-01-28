package com.rank.simplereadforkotlin.di

import androidx.lifecycle.ViewModel
import com.rank.basiclib.di.AppComponent
import com.rank.basiclib.scope.ActivityScope
import dagger.Component
import dagger.android.AndroidInjectionModule
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
@Component(modules = [AndroidInjectionModule::class, BindViewModule::class, BindActivityModule::class, BindFragmentModule::class], dependencies = [AppComponent::class])
interface ModuleComponent {

    fun viewModules(): Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>

    fun inject(app: AppLifecycleImpl)
}