package com.rank.simplereadforkotlin

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.rank.basiclib.di.Injectable
import com.rank.basiclib.ext.CompatActivity
import com.rank.service.Router
import com.rank.service.Router.App.HOME
import com.rank.simplereadforkotlin.databinding.ActivityMainBinding
import com.rank.simplereadforkotlin.viewmodel.MainActivityViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@Route(path = HOME)
class MainActivity : CompatActivity<ActivityMainBinding>(), Injectable {

    override val layoutId: Int = R.layout.activity_main

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java) }

    override fun initViews() {
        GlobalScope.launch {
            delay(1000)
            ARouter.getInstance()
                    .build(Router.WanAndroid.HOME)
                    .navigation()
        }
    }

    override fun initEvents() {

    }

}
