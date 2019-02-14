package com.rank.gank.di

import com.rank.gank.ui.activity.GankHomeActivity
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
abstract class GankBindActivityModule {

    @ContributesAndroidInjector
    abstract fun contributesGankActivity(): GankHomeActivity

}