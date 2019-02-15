package com.rank.basiclib.http

import com.rank.basiclib.log.GlobalHttpHandler
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * <pre>
 *     author: ChenZhaoJun
 *     url  :
 *     time  : 2019/1/16
 *     desc  :
 * </pre>
 */
open class HttpHandlerImpl : GlobalHttpHandler {

    override fun onHttpResultResponse(httpResult: String?, chain: Interceptor.Chain, response: Response): Response {
        return response
    }

    override fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request {
        return request
    }
}