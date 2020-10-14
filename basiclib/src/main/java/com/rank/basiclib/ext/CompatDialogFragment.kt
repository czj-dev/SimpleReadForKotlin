package com.rank.basiclib.ext

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.rank.basiclib.binding.FragmentDataBindingComponent


/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/23
 *     desc  :
 * </pre>
 */
abstract class CompatDialogFragment<B : ViewDataBinding> : AppCompatDialogFragment() {

    private lateinit var rootView: View

    protected lateinit var binding: B

    private var isFirstLoad = true

    abstract val layoutId: Int

    protected fun lazyLoad() = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = LayoutInflater.from(context).inflate(layoutId, container, false)
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DataBindingUtil.bind(rootView, FragmentDataBindingComponent(this))!!
        with(binding) {
            lifecycleOwner = this@CompatDialogFragment
        }
        if (!lazyLoad()) {
            initViews()
            initEvents()
        }
    }

    abstract fun initViews()

    abstract fun initEvents()


    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     *
     * @param isVisibleToUser 是否显示出来了
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            onVisible()
        } else {
            onInvisible()
        }
    }

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     *
     * @param hidden hidden True if the fragment is now hidden, false if it is not
     *               visible.
     */
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            onVisible()
        } else {
            onInvisible()
        }
    }

    /**
     * 当 Fragment 没有显示在界面上时会回调此方法
     */
    open fun onInvisible() {

    }

    /**
     * 当 Fragment 显示在界面上的时候会回调此方法
     */
    open fun onVisible() {
        if (lazyLoad() && isFirstLoad) {
            isFirstLoad = false
            initViews()
            initEvents()
        }
    }
}