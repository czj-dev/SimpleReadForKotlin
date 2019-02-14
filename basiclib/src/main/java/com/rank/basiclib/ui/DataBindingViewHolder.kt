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
class DataBindingViewHolder<DB : ViewDataBinding>(
        val view: View,
        private val dataBindingComponent: DataBindingComponent?
) : BaseViewHolder(view) {

    fun binding(): DB {
        return if (dataBindingComponent != null)
            DataBindingUtil.bind(view, dataBindingComponent)!!
        else
            DataBindingUtil.bind(view)!!
    }

}