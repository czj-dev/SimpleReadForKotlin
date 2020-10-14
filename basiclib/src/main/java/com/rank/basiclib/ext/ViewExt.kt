package com.rank.basiclib.ext

import android.text.TextUtils
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import com.rank.basiclib.utils.ToastUtils


fun message(msg: CharSequence?) {
    if (TextUtils.isEmpty(msg)) {
        return
    }
    ToastUtils.showLong(msg)
}

fun message(@StringRes msg: Int) {
    ToastUtils.showLong(msg)
}

var TextView.textColor: Int
    get() = currentTextColor
    set(v) = setTextColor(v)

fun View.slideExit() {
    if (translationY == 0f) animate().translationY(-height.toFloat())
}

fun View.slideEnter() {
    if (translationY < 0f) animate().translationY(0f)
}

fun View.toVisible(boolean: Boolean) {
    visibility = if (boolean) {
        View.VISIBLE
    } else {
        View.GONE
    }
}