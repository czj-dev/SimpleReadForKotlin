package com.rank.basiclib.binding


import android.app.Activity
import androidx.databinding.DataBindingComponent

/**
 * A Data Binding Component implementation for activity instance.
 */
class ActivityDataBindingComponent(activity: Activity) : DataBindingComponent {

    private val adapters = ActivityBindingAdapter(activity)

    override fun getBindingAdapters() = adapters

}