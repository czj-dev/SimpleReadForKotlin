package com.rank.basiclib.di

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.rank.basiclib.application.AppExecutors
import com.rank.basiclib.data.CurrentUserType
import com.rank.basiclib.data.Domain
import com.rank.basiclib.ext.debug
import com.rank.basiclib.http.SSLSocketClient
import com.rank.basiclib.log.GlobalHttpHandler
import dagger.Module
import dagger.Provides
import me.jessyan.retrofiturlmanager.RetrofitUrlManager
import okhttp3.Authenticator
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
@Module
class HttpClientModule {

    companion object {
        const val TIME_OUT = 30L
    }



    @Provides
    @Singleton
    fun providerClient(interceptors: ArrayList<Interceptor>, handle: GlobalHttpHandler,authenticator: Authenticator?): OkHttpClient {
        val client = OkHttpClient.Builder()
        client.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor { chain: Interceptor.Chain ->
                chain.proceed(
                    handle.onHttpRequestBefore(chain, chain.request())
                )
            }
        for (interceptor in interceptors) {
            client.addInterceptor(interceptor)
        }
        if (authenticator != null) {
            client.authenticator(authenticator)
        }
        if (debug()) {
            client.sslSocketFactory(SSLSocketClient.getSSLSocketFactory())
            client.hostnameVerifier(SSLSocketClient.getHostnameVerifier())
        }
        return client.build()
    }

    private fun configNetworkRequestBaseUrl(client: OkHttpClient.Builder) {
        RetrofitUrlManager.getInstance().apply {
            setGlobalDomain(Domain.RELEASE.apiUrl)
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
            .baseUrl(ApiManager.getInstance().baseUrl)
        return builder.build()
    }

}