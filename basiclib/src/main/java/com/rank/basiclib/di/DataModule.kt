package com.rank.basiclib.di

import android.app.Application
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.rank.basiclib.application.AppManager
import com.rank.basiclib.data.CurrentUser
import com.rank.basiclib.data.CurrentUserType
import com.rank.basiclib.error.ExceptionHandleFactory
import com.rank.basiclib.error.ServiceErrorHandler
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
    fun providerAppManager(): AppManager {
        return AppManager()
    }

    @Singleton
    @Provides
    fun providerErrorHandler(
        application: Application,
        exceptionHandlers: MutableList<ServiceErrorHandler>
    ): ExceptionHandleFactory {
        ExceptionHandleFactory.init(application, exceptionHandlers)
        return ExceptionHandleFactory.instance
    }

    @Singleton
    @Provides
    fun providerCurrentUser(application: Application): CurrentUserType {
        return CurrentUser(application)
    }

}