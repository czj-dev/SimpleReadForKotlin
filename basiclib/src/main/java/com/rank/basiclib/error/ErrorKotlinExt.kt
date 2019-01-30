package com.rank.basiclib.error

import com.rank.basiclib.data.AppResponse
import io.reactivex.Observable

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/30
 *     desc  : rx 错误处理在 Kotlin 上的扩展
 * </pre>
 */
fun <T : AppResponse<*>> Observable<T>.interceptorError(errorHandler: () -> Unit = {}): Observable<T> = this.compose(ExceptionHandleFactory.interceptorError(errorHandler))

fun <T : AppResponse<K>, K> Observable<T>.shelling(errorHandler: () -> Unit = {}): Observable<K> = this.compose(ExceptionHandleFactory.convertData(errorHandler))