package com.rank.simplereadforkotlin.entity.api

import com.rank.simplereadforkotlin.entity.GankResult
import com.rank.simplereadforkotlin.entity.GirlPhoto
import io.reactivex.Observable
import retrofit2.http.GET

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/28
 *     desc  :
 * </pre>
 */
interface MainService {

    @GET("data")
    fun queryLastPhoto(): Observable<GankResult<List<GirlPhoto>>>
}