package com.rank.wanandroid.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.rank.basiclib.Constant
import com.rank.binddepend_annotation.BindDepend
import javax.inject.Inject

@BindDepend(Constant.ClassType.VIEW_MODEl)
class AndroidHomeViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {

}