package com.rank.gank.repository

import com.rank.basiclib.error.shelling
import com.rank.basiclib.http.NetworkManager
import com.rank.gank.api.GankService
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/2/12
 *     desc  :
 * </pre>
 */
class PhotoRepository @Inject constructor(private val networkManager: NetworkManager) {

    fun loadList(page: Int) = networkManager
            .load(GankService::class.java)
            .queryPhoto(page = page)
            .shelling()
            .observeOn(AndroidSchedulers.mainThread())!!
}