package com.rank.gank.viewmodel

import android.app.Application
import androidx.core.util.arrayMapOf
import androidx.lifecycle.AndroidViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/23
 *     desc  :
 * </pre>
 */
class GankActivityViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {


    fun hello() = arrayMapOf(Pair("Hello", "Gank"))

    fun getMessage() = Observable.interval(2, TimeUnit.SECONDS).map { "s" }.observeOn(AndroidSchedulers.mainThread())!!
}