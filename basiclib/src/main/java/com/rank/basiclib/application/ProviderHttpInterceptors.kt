package com.rank.basiclib.application

import okhttp3.Interceptor

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/3/14
 *     desc  :
 * </pre>
 */
interface ProviderHttpInterceptors {
    fun interceptors(): MutableList<Interceptor>?
}