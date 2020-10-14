package com.rank.basiclib.ui

import android.app.Activity
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.graphics.drawable.DrawerArrowDrawable
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.gyf.immersionbar.ImmersionBar
import com.rank.basiclib.R
import com.rank.basiclib.ext.slideEnter
import com.rank.basiclib.ext.slideExit
import com.rank.basiclib.ext.textColor


/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2018/7/27
 *     desc  :
 * </pre>
 */
interface ToolbarManager {

    val toolbar: Toolbar

    var toolbarTitle: String

    fun setNavigationOnClickListener(drawableId: Int, Unit: () -> Unit) {
        setNavigationOnClickListener(ContextCompat.getDrawable(toolbar.context, drawableId), Unit)
    }

    fun setNavigationOnClickListener(drawable: Drawable? = null, Unit: () -> Unit)


    fun setEndIconOnClickListener(drawableId: Int, Unit: () -> Unit) {
        setEndIconOnClickListener(
            text = null,
            drawable = ContextCompat.getDrawable(toolbar.context, drawableId),
            Unit = Unit
        )
    }

    fun setEndIconOnClickListener(text: String?, drawable: Drawable?, Unit: () -> Unit)

    fun removeNavigation()


    class StatusBarManager(
        activity: Activity? = null,
        fragment: Fragment? = null, statusView: View, statusBarColor: Int = android.R.color.white, dark: Boolean = false
    ) {
        init {
            activity?.apply {
                ImmersionBar.with(this).statusBarView(statusView)
                    .statusBarColor(statusBarColor)
                    .statusBarDarkFont(!dark)
                    .init()
            }
            fragment?.apply {
                ImmersionBar.with(this).statusBarView(statusView)
                    .statusBarColor(statusBarColor)
                    .statusBarDarkFont(!dark)
                    .init()
            }
        }

        companion object {
            fun convertStatusBarDark(activity: Activity, dark: Boolean) {
                ImmersionBar.with(activity)
                    .statusBarDarkFont(dark)
                    .init()
            }
        }
    }

    class ToolbarManagerImpl(
        activity: Activity?,
        fragment: Fragment?,
        bar: Toolbar,
        statusView: View,
        statusBarColor: Int,
        dark: Boolean
    ) : ToolbarManager {

        init {
            StatusBarManager(activity, fragment, statusView, statusBarColor, dark)
        }

        private val managerDelegate: ToolbarManager = IOSUIDelegate(bar, dark)


        override var toolbarTitle: String
            get() = managerDelegate.toolbarTitle
            set(value) {
                managerDelegate.toolbarTitle = value
            }

        override val toolbar: Toolbar = managerDelegate.toolbar


        override fun setNavigationOnClickListener(drawable: Drawable?, Unit: () -> Unit) {
            managerDelegate.setNavigationOnClickListener(drawable, Unit)
        }

        override fun removeNavigation() {
            managerDelegate.removeNavigation()
        }

        override fun setEndIconOnClickListener(text: String?, drawable: Drawable?, Unit: () -> Unit) {
            managerDelegate.setEndIconOnClickListener(text, drawable, Unit)

        }

        class Builder {
            var isDark = false
            var statusBarColor: Int = android.R.color.white
            var activity: Activity? = null
            var fragment: Fragment? = null

            fun with(fragment: Fragment): Builder {
                this.fragment = fragment
                return this
            }

            fun with(activity: Activity): Builder {
                this.activity = activity
                return this
            }

            /**
             * 状态栏和标题栏是否为深色
             */
            fun setDark(isDark: Boolean): Builder {
                this.isDark = isDark
                return this
            }

            /**
             * 状态栏颜色，默认为白色
             */
            fun setStatusBarColor(statusBarColor: Int): Builder {
                this.statusBarColor = statusBarColor
                return this
            }

            fun build(): ToolbarManager {
                if (activity == null && fragment == null) {
                    throw NullPointerException("Context params pre requisite ")
                }
                if (!isFindToolbar(activity, fragment)) {
                    throw NullPointerException("You is view requisite include layout_toolbar")
                }
                return ToolbarManagerImpl(
                    activity,
                    fragment,
                    findToolbar(activity, fragment)!!,
                    findStatusView(activity, fragment)!!,
                    statusBarColor,
                    isDark
                )
            }

            private fun isFindToolbar(activity: Activity?, fragment: Fragment?): Boolean {
                return findToolbar(activity, fragment) != null
            }

            private fun findToolbar(activity: Activity?, fragment: Fragment?): Toolbar? {
                if (activity != null) {
                    return activity.findViewById(R.id.toolbar)
                }
                if (fragment != null) {
                    return fragment.view?.findViewById(R.id.toolbar)
                }
                return null
            }

            private fun findStatusView(activity: Activity?, fragment: Fragment?): View? {
                if (activity != null) {
                    return activity.findViewById(R.id.statusView)
                }
                if (fragment != null) {
                    return fragment.view?.findViewById(R.id.statusView)
                }
                return null
            }
        }
    }

    class IOSUIDelegate(override val toolbar: Toolbar, isDark: Boolean) : ToolbarManager {

        private var titleText: TextView
        private var navigationView: ImageButton
        private var endIconView: ImageButton
        private var menuTextView: TextView
        private var color: Int =
            if (isDark) ContextCompat.getColor(toolbar.context,  android.R.color.white) else ContextCompat.getColor(
                toolbar.context,
                R.color.toolbar_text_color
            )

        override fun setNavigationOnClickListener(drawable: Drawable?, Unit: () -> Unit) {
            val background = drawable ?: createUpDrawable()
            navigationView.setImageDrawable(background)
            navigationView.setOnClickListener { Unit() }
        }

        override fun removeNavigation() {
            navigationView.setImageBitmap(null)
            navigationView.setOnClickListener { }
        }

        private fun createUpDrawable() = DrawerArrowDrawable(toolbar.context).apply {
            progress = 1f
            color = this@IOSUIDelegate.color
        }

        init {
            toolbar.setContentInsetsRelative(0, 0)
            val view = LayoutInflater.from(toolbar.context).inflate(R.layout.layout_toolbar_ios, toolbar, false)
            titleText = view.findViewById(R.id.toolbar_title)
            titleText.text = toolbar.title
            titleText.textColor = color
            navigationView = view.findViewById(R.id.navigation_view)
            endIconView = view.findViewById(R.id.menu_view)
            menuTextView = view.findViewById(R.id.menu_text)
            toolbar.addView(view)
        }

        override var toolbarTitle: String = ""
            get() = titleText.text.toString()
            set(value) {
                field = value
                titleText.text = field
            }

        override fun setEndIconOnClickListener(text: String?, drawable: Drawable?, Unit: () -> Unit) {
            if (drawable == null) {
                endIconView.visibility = View.GONE
            } else {
                endIconView.visibility = View.VISIBLE
                endIconView.setImageDrawable(drawable)
                endIconView.setOnClickListener { Unit() }
            }
            if (text == null) {
                menuTextView.visibility = View.GONE
            } else {
                menuTextView.visibility = View.VISIBLE
                menuTextView.text = text
                menuTextView.setOnClickListener { Unit() }
            }
        }
    }

    fun attachToScroll(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) toolbar.slideExit() else toolbar.slideEnter()
            }
        })
    }
}
