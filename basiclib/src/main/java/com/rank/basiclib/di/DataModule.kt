package com.rank.basiclib.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rank.basiclib.error.ExceptionHandleFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/22
 *     desc  :
 * </pre>
 */

@Module
class DataModule {

    @Singleton
    @Provides
    fun providerGson(): Gson {
        return GsonBuilder().serializeNulls().create()
    }

    @Singleton
    @Provides
    fun providerErrorHandler(application: Application): ExceptionHandleFactory {
        return ExceptionHandleFactory(application)
    }

}