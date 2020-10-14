package com.rank.basiclib.rx.transformers

import androidx.lifecycle.LifecycleOwner
import com.trello.rxlifecycle3.android.lifecycle.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/3/15
 *     desc  :
 * </pre>
 */
fun <T> Observable<T>.viewBindAndClickFilter(lifecycleOwner: LifecycleOwner) =
    this.throttleFirst(1, TimeUnit.SECONDS).bindToLifecycle(lifecycleOwner)

fun <T> Observable<T>.delay(createParams: () -> Pair<Long, TimeUnit>) = this.apply {
    val params = createParams()
    this.delay(params.first, params.second)
}
fun <T> Subject<T>.delay(createParams: () -> Pair<Long, TimeUnit>) = this.apply {
    val params = createParams()
    this.delay(params.first, params.second)
}