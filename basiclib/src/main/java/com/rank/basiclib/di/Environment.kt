package com.rank.basiclib.di

import androidx.lifecycle.ViewModelProvider
import com.rank.basiclib.log.GlobalHttpHandler
import com.rank.basiclib.log.RequestInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
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
    lateinit var handler: GlobalHttpHandler

    @Singleton
    @Provides
    fun providerFactory() = factory

    @Provides
    @Singleton
    fun providerHttpHandler() = handler


    @Provides
    @Singleton
    fun providerInterceptor(handler: GlobalHttpHandler): ArrayList<Interceptor> {
        return arrayListOf(RequestInterceptor(handler))
    }
}