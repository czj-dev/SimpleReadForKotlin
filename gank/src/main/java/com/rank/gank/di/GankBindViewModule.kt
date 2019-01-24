package com.rank.gank.di

import androidx.lifecycle.ViewModel
import com.rank.basiclib.di.ViewModelKey
import com.rank.gank.viewmodel.GankActivityViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/23
 *     desc  :
 * </pre>
 */
@Module
abstract class GankBindViewModule {

    @Binds
    @IntoMap
    @ViewModelKey(GankActivityViewModel::class)
    abstract fun bindMainAcitivtyViewModel(viewModel: GankActivityViewModel): ViewModel

}