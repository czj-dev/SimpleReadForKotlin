package com.rank.basiclib.di

import com.rank.basiclib.log.GlobalHttpHandler
import dagger.Module
import dagger.Provides
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
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
    }

    /**
     * 域名表
     */
    enum class DOMAIN(val named: String, val domain: String) {
        GANK(DOMAIN.NAME_GANK, DOMAIN.URL_GANK),
        WANANDROID(DOMAIN.NAME_WANANDROID, DOMAIN.URL_WAN_ANDROID);

        companion object {
            const val NAME_WANANDROID = "wanandroid"
            const val NAME_GANK = "gank"
            const val URL_WAN_ANDROID = "https://www.wanandroid.com/"
            const val URL_GANK = "http://gank.io/api/"
        }
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
        configNetworkRequestBaseUrl(client)
        return client.build()
    }

    private fun configNetworkRequestBaseUrl(client: OkHttpClient.Builder) {
        RetrofitUrlManager.getInstance().apply {
            setGlobalDomain(DOMAIN.GANK.domain)
            putDomain(DOMAIN.GANK.named, DOMAIN.GANK.domain)
            putDomain(DOMAIN.WANANDROID.named, DOMAIN.WANANDROID.domain)
            with(client)
        }
    }

    @Provides
    @Singleton
    fun providerRetrofit(okHttpClient: OkHttpClient): Retrofit {
        val builder = Retrofit.Builder()
        builder.addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .baseUrl(DOMAIN.GANK.domain)
        return builder.build()
    }

}