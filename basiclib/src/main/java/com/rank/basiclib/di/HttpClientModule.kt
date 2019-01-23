package com.rank.basiclib.di

import com.rank.basiclib.log.GlobalHttpHandler
import com.rank.basiclib.log.RequestInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/2
 *     desc  :
 * </pre>
 */
@Module()
 class HttpClientModule {

    companion object {
        const val TIME_OUT = 30L
        const val BASE_URL = ""
    }

    @Provides
    @Singleton
    fun providerRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val builder = Retrofit.Builder()
        builder.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(BASE_URL)
        return builder.build()
    }

    @Provides
    @Singleton
    fun providerClient(interceptors: ArrayList<Interceptor>, handle: GlobalHttpHandler): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addInterceptor { chain: Interceptor.Chain -> chain.proceed(handle.onHttpRequestBefore(chain, chain.request())) }

        for (interceptor in interceptors) {
            client.addInterceptor(interceptor)
        }
        return client.build()
    }

    /**
     * 提供OkHttp拦截器
     */
    @Provides
    @Singleton
    fun providerInterceptor(handler: GlobalHttpHandler): ArrayList<Interceptor> {
        return arrayListOf(RequestInterceptor(handler))
    }

}