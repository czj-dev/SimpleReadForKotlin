package com.rank.basiclib.binding

import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

/**
 * Binding adapters that work with a fragment instance.
 */
class FragmentBindingAdapter(val fragment:Fragment) : BindingAdapters() {

    override fun bindImage(imageView: ImageView, url: String?) {
        Glide.with(fragment).load(url).into(imageView)
    }

}