package com.rank.wanandroid.ui.activity

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.rank.basiclib.di.Injectable
import com.rank.basiclib.ext.CompatActivity
import com.rank.binddepend_annotation.BindDepend
import com.rank.wanandroid.R
import com.rank.wanandroid.databinding.ActivityAndroidHomeBinding
import com.rank.wanandroid.viewmodel.AndroidHomeViewModel
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by 03/07/2019 14:39
 * ================================================
 */
@BindDepend(0)
@Route(path = "wanAndroidHome")
class AndroidHomeActivity : CompatActivity<ActivityAndroidHomeBinding>(), Injectable {

    override val layoutId = R.layout.wan_activity_android_home
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(AndroidHomeViewModel::class.java) }

    override fun initViews() {

    }

    override fun initEvents() {

    }
}