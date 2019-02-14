package com.rank.gank.di

import com.rank.gank.ui.fragment.PhotoFragment
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

    @ContributesAndroidInjector()
    abstract fun contributesPhotoFragment(): PhotoFragment
}