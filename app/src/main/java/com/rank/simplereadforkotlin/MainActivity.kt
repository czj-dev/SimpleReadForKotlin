package com.rank.simplereadforkotlin

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.rank.basiclib.di.Injectable
import com.rank.basiclib.ext.CompatActivity
import com.rank.gank.ui.GankActivity
import com.rank.simplereadforkotlin.databinding.ActivityMainBinding
import com.rank.simplereadforkotlin.view_model.MainActivityViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : CompatActivity<ActivityMainBinding>(), Injectable {


    override val layoutId: Int = R.layout.activity_main

    @Inject
    lateinit var gson: Gson

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel::class.java) }

    override fun initViews() {
    }

    override fun onResume() {
        super.onResume()
        Snackbar.make(binding.root, gson.toJson(viewModel.hello()), Snackbar.LENGTH_LONG).show()
        val intent = Intent(this, GankActivity::class.java)
        GlobalScope.launch {
            delay(1000)
            startActivity(intent)
        }
    }

}
