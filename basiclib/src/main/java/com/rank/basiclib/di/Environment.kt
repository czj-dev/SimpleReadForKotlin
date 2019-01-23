package com.rank.basiclib.di

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/23
 *     desc  :
 * </pre>
 */
@Module
class EnvironmentModule {
    lateinit var factory: ViewModelProvider.Factory

    @Singleton
    @Provides
    fun providerFactory() = factory

}