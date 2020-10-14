package com.rank.basiclib.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.rank.basiclib.binding.BindingComponent

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/2/12
 *     desc  :
 * </pre>
 */
open class DataBindingAdapter<T : Any, DB : ViewDataBinding>(
    layoutId: Int,
    private val dataBindingComponent: BindingComponent? = null,
    protected var callback: (T, DataBindingViewHolder<DB, T>, Int) -> Unit = { _, _, _ -> }
) : BaseQuickAdapter<T, DataBindingViewHolder<DB, T>>(layoutId) {

    override fun convert(helper: DataBindingViewHolder<DB, T>?, item: T) {
        if (helper != null) {
            callback(item, helper, helper.adapterPosition)
        }
    }

    override fun createBaseViewHolder(
        parent: ViewGroup,
        layoutResId: Int
    ): DataBindingViewHolder<DB, T> {
        return DataBindingViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutResId, parent, false),
            dataBindingComponent,
            this
        )
    }
}