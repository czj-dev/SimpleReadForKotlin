package com.rank.simplereadforkotlin.di

import androidx.lifecycle.ViewModel
import com.rank.basiclib.di.ViewModelKey
import com.rank.simplereadforkotlin.view_model.MainActivityViewModel
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
abstract class BindViewModule {
    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainAcitivtyViewModel(viewModel: MainActivityViewModel): ViewModel

}