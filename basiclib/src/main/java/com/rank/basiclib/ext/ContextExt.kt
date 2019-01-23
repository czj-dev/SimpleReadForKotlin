package com.rank.basiclib.ext

import android.content.Context
import com.rank.basiclib.application.BaseApplication
import com.rank.basiclib.di.AppComponent

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/23
 *     desc  :
 * </pre>
 */

fun Context.appComponent(): AppComponent {
    val baseApplication = this.applicationContext as BaseApplication
    return baseApplication.appComponent
}