package com.rank.basiclib.binding


import android.widget.ImageView
import androidx.databinding.BindingAdapter


abstract class BindingAdapters   {

    @BindingAdapter("imageUrl")
    abstract fun bindImage(imageView: ImageView, url: String?)

}