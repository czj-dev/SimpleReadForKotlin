package com.rank.basiclib.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.rank.basiclib.application.AppExecutors
import com.rank.basiclib.application.AppManager
import com.rank.basiclib.application.BaseApplication
import com.rank.basiclib.data.CurrentUserType
import com.rank.basiclib.error.ExceptionHandleFactory
import com.rank.basiclib.http.NetworkManager
import dagger.BindsInstance
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/16
 *     desc  :
 * </pre>
 */
@Singleton
@Component(
    modules = [
        HttpClientModule::class,
        ApplicationBindsModule::class,
        DataModule::class,
        EnvironmentModule::class
    ]
)
interface AppComponent {

    fun factory(): ViewModelProvider.Factory

    fun networkManager(): NetworkManager

    fun exceptionFactory(): ExceptionHandleFactory

    fun application(): Application

    fun appExecutors(): AppExecutors

    fun gson(): Gson

    fun okHttpClient(): OkHttpClient

    fun appManager(): AppManager

    fun currentUser(): CurrentUserType

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun environment(model: EnvironmentModule): Builder

        fun build(): AppComponent
    }

    fun inject(app: BaseApplication)

}