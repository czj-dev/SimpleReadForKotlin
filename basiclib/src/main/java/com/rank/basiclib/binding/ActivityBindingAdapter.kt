package com.rank.basiclib.binding

import android.app.Activity
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Binding adapters that work with a fragment instance.
 */
class ActivityBindingAdapter(val activity: Activity) : BindingAdapters() {

    override fun bindImage(imageView: ImageView, url: String?) {
        Glide.with(activity).load(url).into(imageView)
    }

}