package com.rank.simplereadforkotlin.di

import com.rank.simplereadforkotlin.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/25
 *     desc  :
 * </pre>
 */
@Suppress("unused")
@Module
abstract class BindFragmentModule {

    @ContributesAndroidInjector
    abstract fun contributesMainFragment():MainFragment

}