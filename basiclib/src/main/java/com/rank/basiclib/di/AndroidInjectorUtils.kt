package com.rank.basiclib.di

import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.DispatchingAndroidInjector_Factory
import javax.inject.Provider

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/23
 *     desc  :
 * </pre>
 */
class AndroidInjectorUtils<T> {

    private val injectorViews: HashMap<Class<out T>, Provider<AndroidInjector.Factory<out T>>> = HashMap()

    fun putAll(map:Map<Class<out T>, Provider<AndroidInjector.Factory<out T>>>) {
        injectorViews.putAll(map)
    }

    fun get(): DispatchingAndroidInjector<T> {
        return DispatchingAndroidInjector_Factory.newDispatchingAndroidInjector(injectorViews)
    }
}
