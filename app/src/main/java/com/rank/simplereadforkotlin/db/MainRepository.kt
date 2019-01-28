package com.rank.simplereadforkotlin.db

import com.rank.basiclib.http.NetworkManager
import com.rank.simplereadforkotlin.entity.GirlPhoto
import com.rank.simplereadforkotlin.entity.api.MainService
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/28
 *     desc  :
 * </pre>
 */
class MainRepository @Inject constructor(private val networkManager: NetworkManager) {


    fun obtainLastPhoto(): Observable<GirlPhoto> {
        return networkManager
                .load(MainService::class.java)
                .queryLastPhoto()
                .observeOn(AndroidSchedulers.mainThread())
                .map { data ->
                    data.results[0]
                }
    }

}