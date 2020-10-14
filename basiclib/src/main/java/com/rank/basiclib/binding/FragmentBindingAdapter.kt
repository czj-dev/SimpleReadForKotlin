package com.rank.basiclib.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * Binding adapters that work with a fragment instance.
 */
class FragmentBindingAdapter(val fragment: Fragment) : BindingAdapters() {
    override fun bindImage(imageView: ImageView, url: String?, placeHolder: Drawable?) {
        Glide.with(fragment).load(url).apply {
            if (placeHolder != null) {
                apply(RequestOptions.placeholderOf(placeHolder))
            }
        }.into(imageView)
    }


}