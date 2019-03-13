package com.rank.basiclib.binding


import android.app.Activity

/**
 * A Data Binding Component implementation for activity instance.
 */
class ActivityDataBindingComponent(activity: Activity) : BindingComponent {

    private val adapters = ActivityBindingAdapter(activity)

    override fun getBindingAdapters() = adapters

}