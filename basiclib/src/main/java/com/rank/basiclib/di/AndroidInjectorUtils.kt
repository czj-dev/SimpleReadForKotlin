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
class AndroidInjectorUtils {

    private val classKeyedInjectorFactories: HashMap<Class<*>, Provider<AndroidInjector.Factory<*>>> = HashMap()
    private val stringKeyedInjectorFactories: HashMap<String, Provider<AndroidInjector.Factory<*>>> = HashMap()

    fun putAll(map: Map<Class<*>, Provider<AndroidInjector.Factory<*>>>, stringMap: Map<String, Provider<AndroidInjector.Factory<*>>>) {
        classKeyedInjectorFactories.putAll(map)
        stringKeyedInjectorFactories.putAll(stringMap)
    }


    fun <T> get(): DispatchingAndroidInjector<T> {
        return DispatchingAndroidInjector_Factory.newDispatchingAndroidInjector(classKeyedInjectorFactories, stringKeyedInjectorFactories)
    }
}
