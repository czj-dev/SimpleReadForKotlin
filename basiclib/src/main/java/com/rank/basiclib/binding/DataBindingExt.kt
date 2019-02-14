package com.rank.basiclib.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/24
 *     desc  :
 * </pre>
 */
object DataBindingExt {

    @JvmStatic
    @BindingAdapter("visibleGone")
    fun showHide(view: View, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("bind_adapter")
    fun bindAdapter(recyclerView: RecyclerView, adapter: RecyclerView.Adapter<out androidx.recyclerview.widget.RecyclerView.ViewHolder>?) {
        recyclerView.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("bind_layoutManager")
    fun bindLayoutManager(recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager) {
        recyclerView.layoutManager = layoutManager
    }
}