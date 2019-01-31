package com.rank.basiclib.ext

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.noober.background.BackgroundLibrary


/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/23
 *     desc  :
 * </pre>
 */
abstract class CompatActivity<B : ViewDataBinding> : AppCompatActivity() {

    protected lateinit var binding: B

    private lateinit var rootView: View

    abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        BackgroundLibrary.inject(this)
        super.onCreate(savedInstanceState)
        initViewBinding()
        initViews()
        initEvents()
    }

    private fun initViewBinding() {
        binding = DataBindingUtil.setContentView(this, layoutId)
        with(binding) {
            setLifecycleOwner(this@CompatActivity)
        }
    }

    abstract fun initViews()

    abstract fun initEvents()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (f in supportFragmentManager.fragments) {
            f.onActivityResult(requestCode, resultCode, data)
        }
    }
}