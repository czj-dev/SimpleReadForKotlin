package com.rank.gank.view_model

import android.app.Application
import androidx.core.util.arrayMapOf
import androidx.lifecycle.AndroidViewModel
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
}