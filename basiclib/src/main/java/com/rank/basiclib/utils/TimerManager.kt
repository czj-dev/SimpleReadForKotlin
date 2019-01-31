package com.rank.basiclib.utils

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit


/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/31
 *     desc  :
 * </pre>
 */
class TimerManager private constructor() {

    interface TimerListener {
        /**
         * 倒计时结束回调
         */
        fun timeEnd()
    }

    @Volatile
    private var instance: TimerManager? = null

    fun getInstance(): TimerManager? {
        if (instance == null) {
            synchronized(TimerManager::class.java) {
                if (instance == null) {
                    instance = TimerManager()
                }
            }
        }
        return instance
    }

    /**
     * 创建Observable
     *
     * @param value
     * @param delay
     * @param unit
     * @param <T>
     * @return
    </T> */
    fun <T> getDelayObservable(value: T, delay: Long, unit: TimeUnit): Observable<T> {
        return Observable.just(value)
                .delay(delay, unit)
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * 创建 时延单位秒的Observable
     *
     * @param value
     * @param delay
     * @param <T>
     * @return
    </T> */
    fun <T> getDelayObservable(value: T, delay: Long): Observable<T> {
        return getDelayObservable(value, delay, TimeUnit.SECONDS)
    }

    /**
     * 设置时延为毫秒的定时器
     *
     * @param delayTime
     * @return
     */
    fun setTimer(delayTime: Long): Observable<Long> {
        return getDelayObservable(delayTime, delayTime, TimeUnit.MILLISECONDS)
    }


}