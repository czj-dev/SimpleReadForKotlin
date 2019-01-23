package com.rank.basiclib.di

import com.rank.basiclib.http.NetworkManager
import com.rank.basiclib.log.GlobalHttpHandler
import dagger.Binds
import dagger.Module

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/22
 *     desc  :
 * </pre>
 */
@Module
abstract class ApplicationBindsModule{
    @Binds
    abstract fun bindHttpHandler(networkManager: NetworkManager): GlobalHttpHandler

    @Binds
    abstract fun bindNetworkManager(networkManager: NetworkManager): NetworkManager
/*
    @Binds
    abstract fun bindViewModelFactory(factory: AndroidViewModelFactory): ViewModelProvider.Factory*/
}