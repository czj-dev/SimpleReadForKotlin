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
import com.rank.basiclib.error.ExceptionHandleFactory
import com.rank.basiclib.log.GlobalHttpHandler
import com.rank.basiclib.utils.ViewUtils
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import java.util.*
import javax.inject.Inject
import javax.inject.Provider
import kotlin.collections.HashMap

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/2
 *     desc  : 应用的核心控制，Dagger2和各组件的初始化、注册都在这里
 * </pre>
 */
open class BaseApplication : Application(), HasActivityInjector, HasSupportFragmentInjector {

    private val viewModels: HashMap<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>> = HashMap()

    private lateinit var appLifecycle: ServiceLoader<AppLifecycle>

    private lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    private lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var activityLifecycleCallbacks: ActivityLifecycleCallbacks

    @Inject
    lateinit var exceptionHandleFactory: ExceptionHandleFactory

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
        environmentModule.handler = globalHttpHandler
        environmentModule.serviceErrorHandlers = arrayListOf()
        environmentModule.serviceErrorHandlers.addAll(appLifecycle)

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
        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
        ViewUtils.app = this
        //Timber初始化
        Timber.plant(Timber.DebugTree())
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

    /**
     * 在 OkHttp 处理网络请求之前加工它，和应用层得到 response 之前预处理它
     * 会调用所有注册的组件方法
     */
    private val globalHttpHandler = object : GlobalHttpHandler {
        override fun onHttpResultResponse(httpResult: String?, chain: Interceptor.Chain, response: Response): Response {
            var cResponse: Response = response
            for (appLifecycleObservable in appLifecycle) {
                cResponse = appLifecycleObservable.onHttpResultResponse(httpResult, chain, cResponse)
            }
            return cResponse
        }

        override fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request {
            var cRequest: Request = request
            for (appLifecycleObservable in appLifecycle) {
                cRequest = appLifecycleObservable.onHttpRequestBefore(chain, cRequest)
            }
            return cRequest
        }
    }

}