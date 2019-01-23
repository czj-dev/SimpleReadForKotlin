package com.rank.gank.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.rank.basiclib.di.Injectable
import com.rank.basiclib.ext.CompatActivity
import com.rank.gank.R
import com.rank.gank.view_model.GankActivityViewModel
import javax.inject.Inject

class GankActivity : CompatActivity<com.rank.gank.databinding.ActivityGankBinding>() ,Injectable{

    override val layoutId = R.layout.activity_gank

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(GankActivityViewModel::class.java) }

    override fun initViews() {
    }

    override fun onResume() {
        super.onResume()
        Snackbar.make(binding.root, gson.toJson(viewModel.hello()), Snackbar.LENGTH_LONG).show()
    }
}
