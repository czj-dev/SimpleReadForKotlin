package com.rank.basiclib.ui

import android.view.View
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseViewHolder

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/2/12
 *     desc  :
 * </pre>
 */
class DataBindingViewHolder<DB : ViewDataBinding, T :Any>(
    val view: View,
    private val dataBindingComponent: DataBindingComponent?,
    private val adapter: DataBindingAdapter<T, DB>
) : BaseViewHolder(view) {

    fun adapter(): DataBindingAdapter<T, DB> = adapter

    fun binding(): DB {
        return if (dataBindingComponent != null)
            DataBindingUtil.bind(view, dataBindingComponent)!!
        else
            DataBindingUtil.bind(view)!!
    }

}