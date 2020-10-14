package com.rank.basiclib.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rank.basiclib.ext.toVisible

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
    fun bindAdapter(
        recyclerView: RecyclerView,
        adapter: RecyclerView.Adapter<out androidx.recyclerview.widget.RecyclerView.ViewHolder>?
    ) {
        recyclerView.adapter = adapter
    }

    @JvmStatic
    @BindingAdapter("bind_layoutManager")
    fun bindLayoutManager(recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager) {
        recyclerView.layoutManager = layoutManager
    }

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageViewResource(imageView: ImageView, resource: Int) {
        imageView.setImageResource(resource)
    }

    @JvmStatic
    @BindingAdapter("selected")
    fun selected(view: View, selected: Boolean) {
        view.isSelected = selected
    }

    @JvmStatic
    @BindingAdapter("isVisible")
    fun isVisible(view: View, isVisible: Boolean) {
        view.toVisible(isVisible)
    }


}