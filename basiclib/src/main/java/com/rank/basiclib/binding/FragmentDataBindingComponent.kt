package com.rank.basiclib.binding


import androidx.fragment.app.Fragment

/**
 * A Data Binding Component implementation for fragments.
 */
class FragmentDataBindingComponent(fragment: Fragment) : BindingComponent {
    override fun getBindingAdapters()=adapter
    private val adapter = FragmentBindingAdapter(fragment)
}
