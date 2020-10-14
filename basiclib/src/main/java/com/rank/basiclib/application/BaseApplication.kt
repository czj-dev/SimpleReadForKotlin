package com.rank.basiclib.application

import android.app.Activity
import android.app.Application
import android.content.Context
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
//import androidx.multidex.MultiDex
import com.alibaba.android.arouter.launcher.ARouter
import com.didichuxing.doraemonkit.DoraemonKit
import com.facebook.stetho.Stetho
import com.rank.basiclib.binding.DefaultAdapters
import com.rank.basiclib.di.*
import com.rank.basiclib.error.ExceptionHandleFactory
import com.rank.basiclib.ext.debug
import com.rank.basiclib.log.GlobalHttpHandler
import com.rank.basiclib.log.RequestInterceptor
import com.rank.basiclib.utils.Utils
import com.rank.basiclib.utils.ViewUtils
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import dagger.android.support.HasSupportFragmentInjector
import me.jessyan.autosize.AutoSizeConfig
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
//        MultiDex.install(base)
//        Beta.installTinker()
        //通过 ServiceLoader 来装载各个 Module 的 AppLifecycle
        appLifecycle = ServiceLoader.load(AppLifecycle::class.java)
        for (appLifecycleObservable in appLifecycle) {
            appLifecycleObservable.attachBaseContext(this)
        }
    }

    override fun onCreate() {
        super.onCreate()
        ViewUtils.app = this
        Utils.init(this)
        val environmentModule = EnvironmentModule()
        environmentModule.handler = globalHttpHandler
        environmentModule.serviceErrorHandlers = arrayListOf()
        environmentModule.interceptors = arrayListOf()
        environmentModule.interceptors.add(RequestInterceptor(globalHttpHandler))
        appComponent = AppInjector.init(this, environmentModule)
        val injectUtils = AndroidInjectorUtils()
        for (appLifecycleObservable in appLifecycle) {
            appLifecycleObservable.providerConfig().apply {
                this.httpInterceptor?.run {
                    environmentModule.interceptors.addAll(this)
                }
                this.handlerServiceError.run {
                    environmentModule.serviceErrorHandlers.add(this)
                }
                if (this.authenticator != null) {
                    environmentModule.authenticator = this.authenticator
                }
            }

            appLifecycleObservable.onCreate(this)

            //获取当前 Module 中注册的 viewModel
            val moduleViewModels = appLifecycleObservable.returnViewModels()
            this.viewModels.putAll(moduleViewModels)

            //获取当前注册的视图组件中注入总Component
            injectUtils.putAll(
                appLifecycleObservable.classKeyedInjectorFactories(),
                appLifecycleObservable.stringKeyedInjectorFactories()
            )

        }

        //将注入的 ViewModel 集中放置到 Factory 中
        environmentModule.factory = AndroidViewModelFactory(viewModels)

        //将注入的 Activity 放置等待被调起
        dispatchingAndroidInjector = injectUtils.get()

        //将注入的 Fragment 放置插件中等待被调起
        fragmentDispatchingAndroidInjector = injectUtils.get()

        registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
        ARouter.init(this)
        Bugly.init(this, "a9c7ec9ea0", true)
        AutoSizeConfig.getInstance()
            .setLog(debug())
            .init(this)
            .setUseDeviceSize(false)
            .setCustomFragment(true)
            .setExcludeFontScale(true)
            .setDesignHeightInDp(DESIGN_HEIGHT)
            .setDesignWidthInDp(DESIGN_WIDTH)
        DataBindingUtil.setDefaultComponent(DefaultAdapters.defaultComponent())
        buildConfig(debug(), debugConfig(), releaseConfig())
    }

    private fun releaseConfig(): () -> Unit = {

    }

    private fun debugConfig(): () -> Unit = {
        //Timber初始化
        Timber.plant(Timber.DebugTree())
        ARouter.openLog()
        ARouter.openDebug()
        Stetho.initializeWithDefaults(this)
        DoraemonKit.install(this)
    }

    private fun buildConfig(isDebug: Boolean, debugFunction: () -> Unit, releaseFunction: () -> Unit) {
        if (isDebug) {
            debugFunction()
        } else {
            releaseFunction()
        }
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
                cResponse = appLifecycleObservable.providerConfig().onHttpResultResponse(httpResult, chain, cResponse)
            }
            return cResponse
        }

        override fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request {
            var cRequest: Request = request
            for (appLifecycleObservable in appLifecycle) {
                cRequest = appLifecycleObservable.providerConfig().onHttpRequestBefore(chain, cRequest)
            }
            return cRequest
        }
    }

    companion object {
        //设计稿 iphone6 的宽高尺寸
        const val DESIGN_HEIGHT = 667
        const val DESIGN_WIDTH = 375
    }

}