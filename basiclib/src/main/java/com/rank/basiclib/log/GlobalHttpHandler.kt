package com.rank.basiclib.log

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2018/9/11
 *     desc  :
 * </pre>
 */
interface GlobalHttpHandler {

    fun onHttpResultResponse(httpResult: String?, chain: Interceptor.Chain, response: Response): Response

    fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request

}