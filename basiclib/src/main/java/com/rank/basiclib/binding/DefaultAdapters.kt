package com.rank.basiclib.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/4/16
 *     desc  :
 * </pre>
 */

class DefaultAdapters : BindingAdapters() {

    override fun bindImage(imageView: ImageView, url: String?, placeHolder: Drawable?) {
        Glide.with(imageView).load(url).apply {
            if (placeHolder != null) {
                apply(RequestOptions.placeholderOf(placeHolder))
            }
        }.into(imageView)
    }


    companion object {
        fun defaultComponent() = object : BindingComponent {
            override fun getBindingAdapters(): BindingAdapters {
                return DefaultAdapters()
            }
        }
    }
}