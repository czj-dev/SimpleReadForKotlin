package com.rank.basiclib.data

import androidx.lifecycle.LifecycleOwner
import com.trello.rxlifecycle3.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/4/18
 *     desc  : 项目时间总线处理
 * </pre>
 */
 object EventBus {
    private var subject: Subject<Any> = BehaviorSubject.create()
    private var stickySubject: Subject<Any> = PublishSubject.create()

    fun postSticky(any: Any) {
        stickySubject.onNext(any)
    }

    fun post(any: Any) {
        subject.onNext(any)
    }

    fun <T> stickyObservable(clazz: Class<T>): Observable<T> = stickySubject.ofType(clazz)

    fun <T> observable(clazz: Class<T>): Observable<T> = subject.ofType(clazz)


    fun <T> stickyObservable(clazz: Class<T>, lifecycleOwner: LifecycleOwner): Observable<T> =
        EventBus.stickyObservable(clazz).bindToLifecycle(lifecycleOwner)

    fun <T> observable(clazz: Class<T>, lifecycleOwner: LifecycleOwner) =
        EventBus.observable(clazz).bindToLifecycle(lifecycleOwner)

}