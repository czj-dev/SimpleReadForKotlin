package com.rank.basiclib.binding


import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter


abstract class BindingAdapters {

    @BindingAdapter("imageUrl", "placeHolder", requireAll = false)
    abstract fun bindImage(imageView: ImageView, url: String?, placeHolder: Drawable?)

}