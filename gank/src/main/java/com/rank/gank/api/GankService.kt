package com.rank.gank.api

import com.rank.gank.vo.GankResult
import com.rank.gank.vo.GirlPhoto
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/2/12
 *     desc  :
 * </pre>
 */
interface GankService {

    @GET("data/福利/{sum}/{page}")
    fun queryPhoto(
            @Path("sum") sum: Int = 10,
            @Path("page") page: Int):
            Observable<GankResult<List<GirlPhoto>>>
}