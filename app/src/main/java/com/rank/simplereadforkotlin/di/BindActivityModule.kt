package com.rank.simplereadforkotlin.di

import com.rank.simplereadforkotlin.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/23
 *     desc  :
 * </pre>
 */
@Module
abstract class BindActivityModule {


    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity

}