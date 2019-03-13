package com.rank.wanandroid.data.repository

import com.rank.basiclib.error.shelling
import com.rank.basiclib.http.NetworkManager
import com.rank.wanandroid.data.api.WanAndroidService
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/3/12
 *     desc  :
 * </pre>
 */
class ArticleRepository @Inject constructor(private val networkManager: NetworkManager) {

    fun loadList(page: Int) = networkManager
            .load(WanAndroidService::class.java)
            .articleList(page = page)
            .shelling()
            .observeOn(AndroidSchedulers.mainThread())!!
}