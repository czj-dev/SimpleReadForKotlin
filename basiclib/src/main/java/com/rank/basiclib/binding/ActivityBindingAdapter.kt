package com.rank.basiclib.binding

import android.app.Activity
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Binding adapters that work with a fragment instance.
 */
class ActivityBindingAdapter(val activity: Activity) : BindingAdapters() {

    override fun bindImage(imageView: ImageView, url: String?, placeHolder: Drawable?) {
        Glide.with(activity).load(url).apply {
            if (placeHolder != null) {
                apply(RequestOptions.placeholderOf(placeHolder))
            }
        }.into(imageView)
    }
}