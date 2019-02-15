package com.rank.basiclib.log

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2018/9/11
 *     desc  :  应用的网络中间层接口
 * </pre>
 */
interface GlobalHttpHandler {

    /**
     * 每个网络请求在交由应用层处理前会转交给接口预处理
     */
    fun onHttpResultResponse(httpResult: String?, chain: Interceptor.Chain, response: Response): Response

    /**
     * 每个网络请求在交由 OkHttp 请求前都会交由接口预处理
     */
    fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request

}