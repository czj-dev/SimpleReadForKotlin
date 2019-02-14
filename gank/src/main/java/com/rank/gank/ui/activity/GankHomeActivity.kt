package com.rank.gank.ui.activity

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.rank.basiclib.di.Injectable
import com.rank.basiclib.ext.CompatActivity
import com.rank.gank.R
import com.rank.gank.databinding.GankActivityHomeBinding
import com.rank.gank.viewmodel.HomeActivityViewModel
import javax.inject.Inject


@Route(path = "/gank/home")
class GankHomeActivity : CompatActivity<GankActivityHomeBinding>(), Injectable {

    override val layoutId = R.layout.gank_activity_home

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(HomeActivityViewModel::class.java) }

    override fun initViews() {
        val fragment = ARouter.getInstance().build("/gank/photo").navigation() as Fragment
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commitAllowingStateLoss()
        setSupportActionBar(binding.toolbar)
    }

    override fun initEvents() {

    }
}
