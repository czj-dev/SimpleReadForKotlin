package com.rank.wanandroid.di

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
 *     time  : 2019/3/11
 *     desc  :
 * </pre>
 */
@ActivityScope
@Component(modules = [AndroidInjectionModule::class, com.rank.wanandroid.bind.BindActivityModule::class, com.rank.wanandroid.bind.BindViewModelModule::class], dependencies = [AppComponent::class])
interface ModuleComponent {

    fun viewModules(): Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>

    fun inject(app: AppLifecycleImpl)
}