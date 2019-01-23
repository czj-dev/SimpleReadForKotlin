package com.rank.basiclib.ext

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

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
        super.onCreate(savedInstanceState)
        initViewBinding()
        initViews()
    }

    abstract fun initViews()

    private fun initViewBinding() {
        binding = DataBindingUtil.setContentView(this, layoutId)
        with(binding) {
            setLifecycleOwner(this@CompatActivity)
        }
    }
}