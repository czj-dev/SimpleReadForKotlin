package com.rank.gank.ui

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.rank.basiclib.di.Injectable
import com.rank.basiclib.ext.CompatActivity
import com.rank.gank.R
import com.rank.gank.viewmodel.GankActivityViewModel
import com.trello.rxlifecycle3.android.lifecycle.kotlin.bindToLifecycle
import javax.inject.Inject

@Route(path = "/gank/home")
class GankActivity : CompatActivity<com.rank.gank.databinding.ActivityGankBinding>(), Injectable {

    override val layoutId = R.layout.activity_gank

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(GankActivityViewModel::class.java) }

    override fun initViews() {
    }

    @SuppressLint("CheckResult")
    override fun onResume() {
        super.onResume()
        viewModel.getMessage()
                .bindToLifecycle(this)
                .subscribe { Snackbar.make(binding.root, gson.toJson(it), Snackbar.LENGTH_SHORT).show() }
    }
}
