package com.rank.basiclib.application

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.rank.basiclib.BuildConfig
import com.rank.basiclib.di.*
import com.rank.basiclib.log.GlobalHttpHandler
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.*
import javax.inject.Provider
import kotlin.collections.HashMap

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/2
 *     desc  :
 * </pre>
 */
open class BaseApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    private val viewModels: HashMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>> = HashMap()

    private lateinit var appLifecycle: ServiceLoader<AppLifecycle>

    private lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    private lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    lateinit var appComponent: AppComponent

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(base)
        //通过 ServiceLoader 来装载各个 Module 的 AppLifecycle
        appLifecycle = ServiceLoader.load(AppLifecycle::class.java)
        for (appLifecycleObservable in appLifecycle) {
            appLifecycleObservable.attachBaseContext(this)
        }
    }

    override fun onCreate() {
        super.onCreate()
        val environmentModule = EnvironmentModule()
        environmentModule.handler=object :GlobalHttpHandler{
            override fun onHttpResultResponse(httpResult: String?, chain: Interceptor.Chain, response: Response): Response {
                return response
            }

            override fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request {
                return request
            }
        }
        appComponent = AppInjector.init(this, environmentModule)

        val injectUtils = AndroidInjectorUtils()
        for (appLifecycleObservable in appLifecycle) {

            appLifecycleObservable.onCreate(this)

            //获取当前 Module 中注册的 viewModel
            val moduleViewModels = appLifecycleObservable.returnViewModels()
            this.viewModels.putAll(moduleViewModels)

            //获取当前注册的视图组件中注入总Component
            injectUtils.putAll(appLifecycleObservable.classKeyedInjectorFactories(), appLifecycleObservable.stringKeyedInjectorFactories())

        }

        //将注入的 ViewModel 集中放置到 Factory 中
        environmentModule.factory = AndroidViewModelFactory(viewModels)

        //将注入的 Activity 放置等待被调起
        dispatchingAndroidInjector = injectUtils.get()

        //将注入的 Fragment 放置插件中等待被调起
        fragmentDispatchingAndroidInjector = injectUtils.get()

        initARouter()
    }

    private fun initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector

    override fun supportFragmentInjector() = fragmentDispatchingAndroidInjector

    override fun onTerminate() {
        super.onTerminate()
        for (appLifecycleObservable in appLifecycle) {
            appLifecycleObservable.onTerminate(this)
        }
    }

}