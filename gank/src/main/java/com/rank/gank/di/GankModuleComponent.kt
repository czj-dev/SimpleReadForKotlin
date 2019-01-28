package com.rank.gank.di

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
@Component(modules = [AndroidInjectionModule::class,GankBindViewModule::class, GankBindActivityModule::class], dependencies = [AppComponent::class])
interface GankModuleComponent {

    fun viewModules(): Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>

    fun inject(app: GankAppLifecycleImpl)
}