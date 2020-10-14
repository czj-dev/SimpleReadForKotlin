package com.rank.basiclib.ext

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gyf.immersionbar.ImmersionBar
import com.rank.basiclib.R
import com.rank.basiclib.binding.ActivityDataBindingComponent


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
        initEvents()
    }

    private fun initViewBinding() {
        binding = DataBindingUtil.setContentView(this, layoutId, ActivityDataBindingComponent(this))
        with(binding) {
            lifecycleOwner = this@CompatActivity
        }
        if (findViewById<View>(R.id.statusView) != null) {
            val immersionBar = ImmersionBar.with(this)
            immersionBar.statusBarDarkFont(true)
                .statusBarView(R.id.statusView)
                .init()
        }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }


    abstract fun initViews()

    abstract fun initEvents()

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
//        for (f in supportFragmentManager.fragments) {
//            if (f == null) {
//                continue
//            }
//            f.onActivityResult(requestCode, resultCode, data)
//        }
    }
}