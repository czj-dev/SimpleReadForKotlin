package com.rank.basiclib.di

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.rank.basiclib.application.BaseApplication
import dagger.BindsInstance
import dagger.Component
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
@Component(modules = [
    HttpClientModule::class,
    ApplicationBindsModule::class,
    DataModule::class,
    EnvironmentModule::class
])
interface AppComponent {

    fun factory(): ViewModelProvider.Factory

    fun application(): Application

    fun gson(): Gson

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(app: Application): Builder

        fun environment(model: EnvironmentModule): Builder

        fun build(): AppComponent
    }

    fun inject(app: BaseApplication)

}