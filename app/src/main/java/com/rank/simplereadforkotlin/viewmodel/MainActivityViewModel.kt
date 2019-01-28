package com.rank.simplereadforkotlin.viewmodel

import android.app.Application
import androidx.core.util.arrayMapOf
import androidx.lifecycle.AndroidViewModel
import com.rank.simplereadforkotlin.db.MainRepository
import javax.inject.Inject

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/23
 *     desc  :
 * </pre>
 */
class MainActivityViewModel @Inject constructor(application: Application, private val mainRepository: MainRepository) : AndroidViewModel(application) {


    fun hello() = arrayMapOf(Pair("Hello", "Main"))


    fun queryPhoto() = mainRepository.obtainLastPhoto()

}